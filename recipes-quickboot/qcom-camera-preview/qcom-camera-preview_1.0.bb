# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: BSD-3-Clause-Clear
#
# Recipe for the QuickBoot early camera preview demo application.
#
# Installs:
#   - camera-preview          → ${bindir}/              (camera preview script)
#   - camera-preview.service  → ${systemd_unitdir}/system/ (camera preview service)
#
# The service starts camera preview when cam-server and Weston are ready,
# acting as a time-to-first-frame measurement checkpoint for QuickBoot profiling.
#
# Note: Service is installed but NOT auto-enabled. Enable manually with:
#   systemctl enable camera-preview.service
#
# Enabled only when DISTRO_FEATURES includes "quickboot" and "quickboot-camera".

SUMMARY = "Qualcomm QuickBoot Early Camera Preview"
DESCRIPTION = "Displays a live camera preview via Weston immediately after the \
camera pipeline and display are ready. Used as a time-to-first-frame measurement \
checkpoint for QuickBoot profiling."

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=2998c54c288b081076c9af987bdf4838"

SRC_URI = "git://github.com/qualcomm/quickboot.git;protocol=https;branch=main"
SRCREV = "969ed193bff4f491b98b52cdbc864956ac394549"

# Source subdirectory within the cloned repo
S = "${UNPACKDIR}/qcom-camera-preview-${PV}/qcom-camera-preview"

inherit meson systemd

# ── Meson options ─────────────────────────────────────────────────────────────
# Pass the Yocto-resolved systemd unit directory so meson does not need
# pkg-config auto-detection (which may not work in all cross environments).
EXTRA_OEMESON = "-Dsystemd_system_unit_dir=${systemd_unitdir}/system"

# ── Installed files ───────────────────────────────────────────────────────────
FILES:${PN} = " \
    ${bindir}/camera-preview \
    ${systemd_unitdir}/system/camera-preview.service \
"

SYSTEMD_SERVICE:${PN} = "camera-preview.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"
