#!/bin/sh
# Copyright (c) 2025 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: BSD-3-Clause-Clear

# Exit on error
set -eu

# Configurable paths
SERIAL_FILE=${SERIAL_FILE:-/sys/devices/soc0/serial_number}
TMP_LOG=$(mktemp /tmp/btmgmt_output.XXXXXX)

# Cleanup on exit
trap 'rm -f "$TMP_LOG"' EXIT

# Debug mode (set DEBUG=1 to enable)
DEBUG=${DEBUG:-0}

# Choose an official Qualcomm 3-byte Organizationally Unique Identifier(OUI) 
# See IEEE/lookup citations: https://standards-oui.ieee.org/.
OUI="A0:BD:71"

debug_log() {
    if [ "$DEBUG" -eq 1 ]; then
        echo "[DEBUG] $*" >&2
    fi
}

# Helper to log errors with timestamps
log_error() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') ERROR: $*" >&2
}

# Verify required external commands
REQUIRED_CMDS="printf sed grep hciconfig bluetoothctl btmgmt awk"
for cmd in $REQUIRED_CMDS; do
    if ! command -v "$cmd" >/dev/null 2>&1; then
        log_error "Required command '$cmd' not found"
        exit 1
    fi
done

# Ensure script is run as root
if [ "$(id -u)" -ne 0 ]; then
    log_error "This script must be run as root"
    exit 1
fi

set_bda() {
    # Read serial number
    if [ ! -f "$SERIAL_FILE" ]; then
        log_error "Serial number file not found: $SERIAL_FILE"
        return 1
    fi

    serial_number=$(cat "$SERIAL_FILE")
    debug_log "Serial number: $serial_number"

    if [ -z "$serial_number" ]; then
        log_error "Serial number is empty"
        return 1
    fi

    case "$serial_number" in
        *[!0-9]*)
            log_error "Serial number is not numeric"
            return 1
            ;;
    esac

    # Extract exactly 6 hex characters (3 bytes) from the serial number.
    # If fewer than 6 digits, pad with leading zero
    dev_suffix_hex=$(printf "%06X" "$serial_number" | awk '{print substr($0, length($0)-5)}')
    debug_log "Padded hex serial number: $dev_suffix_hex"

    # Validate non-empty and not all zeros
    if [ -z "$dev_suffix_hex" ] || [ "$dev_suffix_hex" = "000000" ]; then
        log_error "Invalid device suffix: $dev_suffix_hex"
        return 1
    fi

    # Format as colon-separated bytes
    dev_suffix=$(echo "$dev_suffix_hex" | sed 's/\(..\)/\1:/g;s/:$//')
    debug_log "Formatted serial number: $dev_suffix"

    # Build BD_ADDR
    BDA="$OUI:$dev_suffix"
    debug_log "Target BDA: $BDA"

    # Validate BD_ADDR format (must be 6 octets)
    if ! echo "$BDA" | grep -Eq '^([0-9A-F]{2}:){5}[0-9A-F]{2}$'; then
        log_error "Invalid BDA format: $BDA"
        return 1
    fi

    sleep 1

    # Set BD address using btmgmt
    {
        echo "public-addr $BDA"
        sleep 1
    } | btmgmt >"$TMP_LOG" 2>&1
    btmgmt_status=$?

    if [ "$btmgmt_status" -ne 0 ]; then
        log_error "btmgmt failed (exit $btmgmt_status)"
        cat "$TMP_LOG" >&2
        return 1
    fi

    if ! grep -q "public-addr $BDA" "$TMP_LOG"; then
        log_error "btmgmt output does not confirm address set"
        cat "$TMP_LOG" >&2
        return 1
    fi

    debug_log "BD address successfully set to $BDA"
    return 0
}

validate_and_set_bda() {
    attempts=0
    max_attempts=10

    while [ "$attempts" -lt "$max_attempts" ]; do
        hciconfig_output=$(hciconfig)
        echo "$hciconfig_output" | grep -q 'BD Address' || {
            log_error "hciconfig output missing BD Address"
            return 1
        }

        bd_address=$(echo "$hciconfig_output" | grep 'BD Address' | awk '{print $3}')
        debug_log "Current BD Address: $bd_address"
        unconfigured=$(echo "$hciconfig_output" | grep -o 'DOWN RAW')
        configured=$(echo "$hciconfig_output" | grep -o 'DOWN')

        # Check if the BD Address is 00:00:00:00:00:00
        if [ "$bd_address" = "00:00:00:00:00:00" ]; then
            sleep 1
        elif  [[ "$unconfigured" == "DOWN RAW" ]]; then
           break
        elif [[ "$configured" == "DOWN" ]]; then
           echo "BD-Address already configured!!"
           return 0
        else
            break
        fi

        sleep 1
        attempts=$((attempts + 1))
    done

    if [ "$attempts" -ge "$max_attempts" ]; then
        log_error "Max attempts reached without configuring BD address"
        return 1
    fi

    if ! set_bda; then
        log_error "set_bda failed"
        return 1
    fi

    return 0
}

# Check bluetoothctl output
bluetoothctl_output=$(bluetoothctl show || true)
echo "$bluetoothctl_output" | grep -q "No default controller available" && {
    if ! validate_and_set_bda; then
        log_error "validate_and_set_bda failed"
        exit 1
    fi
}
