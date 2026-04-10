#!/bin/sh
# SPDX-License-Identifier: MIT
# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# Create virtual WiFi interfaces for NetworkManager system-connections

TAG="wifi-vif"
CONN_DIR="/etc/NetworkManager/system-connections"
TIMEOUT=30

# ------------------------------------------------------------
# Logging helpers
# ------------------------------------------------------------
log() {
    logger -t "$TAG" "$@"
}

log_err() {
    logger -t "$TAG" "ERROR: $@"
}

# ------------------------------------------------------------
# Helper functions
# ------------------------------------------------------------

# Find NM interface-name that actually exists in `iw dev`
get_base_iface() {
    iw dev 2>/dev/null | awk '/Interface/ {print $2}' |
    while read -r iw_iface; do
        for f in "$CONN_DIR"/*.nmconnection; do
            [ -f "$f" ] || continue
            nm_iface=$(sed -n 's/^interface-name=//p' "$f" | head -n1)
            [ "$iw_iface" = "$nm_iface" ] && echo "$iw_iface"
        done
    done | head -n1
}

# Verify BASE_IFACE is in STA mode and wait until it associates.
wait_for_sta_connected() {
    iface="$1"

    # Bail out early if not in managed (STA) mode
    iw dev "$iface" info 2>/dev/null | grep -q "type managed" || {
        log "WARNING: $iface is not in STA mode, skip waiting"
        return 1
    }

    log "Waiting for $iface to connect (timeout=${TIMEOUT}s)"
    i=0
    while [ "$i" -lt "$TIMEOUT" ]; do
        iw dev "$iface" link 2>/dev/null | grep -q "Connected to" && {
            log "$iface is connected"
            return 0
        }
        sleep 1; i=$((i + 1))
    done

    log_err "Timeout waiting for $iface to connect"
    return 1
}

# Wait until any interface enters AP mode
wait_for_ap() {
    i=0
    log "Waiting for AP mode (timeout=${TIMEOUT}s)"

    while [ "$i" -lt "$TIMEOUT" ]; do
        for iface in $(iw dev 2>/dev/null | awk '/Interface/ {print $2}'); do
            if iw dev "$iface" info 2>/dev/null | grep -q "type AP"; then
                log "AP mode detected on interface $iface"
                return 0
            fi
        done

        sleep 1
        i=$((i + 1))
    done

    log_err "Timeout waiting for AP mode"
    return 1
}

# Get configured mode (ap / infrastructure) for an interface
get_vif_mode() {
    iface="$1"

    for f in "$CONN_DIR"/*.nmconnection; do
        [ -f "$f" ] || continue
        grep -qx "interface-name=$iface" "$f" || continue

        sed -n '/^\[wifi\]$/,/^\[/{s/^mode=//p}' "$f" | head -n1
        return
    done

    echo "infrastructure"
}

# Collect VIFs declared in NM (excluding base iface)
collect_vifs() {
    for f in "$CONN_DIR"/*.nmconnection; do
        [ -f "$f" ] || continue

        conn_type=$(sed -n 's/^type=//p' "$f" | head -n1)
        iface=$(sed -n 's/^interface-name=//p' "$f" | head -n1)

        [ "$conn_type" = "wifi" ] &&
        [ -n "$iface" ] &&
        [ "$iface" != "$BASE_IFACE" ] &&
        echo "$iface"
    done
}

# ------------------------------------------------------------
# Main
# ------------------------------------------------------------

log "==== start ===="

BASE_IFACE=$(get_base_iface)

log "BASE_IFACE=$BASE_IFACE"
log "CONN_DIR=$CONN_DIR"
log "TIMEOUT=$TIMEOUT"

[ -z "$BASE_IFACE" ] && {
    log_err "Failed to detect base WiFi interface"
    exit 1
}

vif_list=$(
    collect_vifs |
    sort -u |
    while read -r iface; do
        if ip link show "$iface" >/dev/null 2>&1; then
            log "Interface $iface already exists, skip"
        else
            log "Interface $iface needs to be created"
            echo "$iface"
        fi
    done
)

vif_count=$(printf "%s\n" "$vif_list" | grep -c .)

log "vif_list:"
printf "%s\n" "$vif_list" | while read -r l; do log "  $l"; done
log "vif_count=$vif_count"

# Warn if BASE_IFACE is not STA mode; poll until connected or timeout
wait_for_sta_connected "$BASE_IFACE" || true

case "$vif_count" in
    0)
        log "No virtual interfaces to create"
        ;;
    1)
        iface=$(printf "%s\n" "$vif_list")
        iw dev "$BASE_IFACE" interface add "$iface" type managed || exit 1
        ;;
    2)
        first=$(printf "%s\n" "$vif_list" | sed -n '1p')
        second=$(printf "%s\n" "$vif_list" | sed -n '2p')

        # Ensure AP interface is created first
        [ "$(get_vif_mode "$first")" != "ap" ] && {
            tmp="$first"; first="$second"; second="$tmp"
        }

        iw dev "$BASE_IFACE" interface add "$first" type managed || exit 1
        wait_for_ap || exit 1
        iw dev "$BASE_IFACE" interface add "$second" type managed || exit 1
        ;;
    *)
        log_err "Too many VIFs requested ($vif_count), max supported is 2"
        exit 1
        ;;
esac

log "==== end ===="
exit 0
