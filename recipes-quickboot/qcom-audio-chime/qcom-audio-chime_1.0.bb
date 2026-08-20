# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: BSD-3-Clause-Clear
#
# Recipe for the QuickBoot early audio chime demo application.
#
# The service plays the boot chime once PipeWire is ready, acting as a
# time-to-first-sound measurement checkpoint for QuickBoot profiling.
#
# Note: Service is installed but NOT auto-enabled. Enable manually with:
#   systemctl enable audio-chime.service

SUMMARY = "Qualcomm QuickBoot Early Audio Chime"
DESCRIPTION = "Plays a boot chime via PipeWire immediately after the audio \
pipeline is ready. Used as a time-to-first-sound measurement checkpoint \
for QuickBoot profiling."

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=2998c54c288b081076c9af987bdf4838"

SRC_URI = "git://github.com/qualcomm/quickboot.git;protocol=https;branch=main"
SRCREV = "969ed193bff4f491b98b52cdbc864956ac394549"

# Source subdirectory within the cloned repo
S = "${UNPACKDIR}/qcom-audio-chime-${PV}/qcom-audio-chime"

inherit meson systemd

# ── Meson options ─────────────────────────────────────────────────────────────
# Pass the Yocto-resolved systemd unit directory so meson does not need
# pkg-config auto-detection (which may not work in all cross environments).
EXTRA_OEMESON = "-Dsystemd_system_unit_dir=${systemd_unitdir}/system"

# ── Installed files ───────────────────────────────────────────────────────────
FILES:${PN} = " \
    ${bindir}/audio-chime \
    ${datadir}/sounds/sample-3s.wav \
    ${systemd_unitdir}/system/audio-chime.service \
"

SYSTEMD_SERVICE:${PN} = "audio-chime.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

# Requires PipeWire at runtime for audio playback
RDEPENDS:${PN} = "pipewire bash"
