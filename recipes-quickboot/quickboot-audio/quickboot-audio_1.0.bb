# This bitbake recipe that packages and installs the quickboot
# audio optimizations. It ensures the custom 'modules-load.d'
# configuration, udev rules, early systemd services and the
# chime wav file are deployed to the rootfs.

SUMMARY = "Early Audio Boot Optimizations"
DESCRIPTION = "Installs kernel module load list for faster audio driver probes during boot"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=2998c54c288b081076c9af987bdf4838"

SRC_URI = "git://github.com/qualcomm/quickboot.git;protocol=https;branch=main"
SRCREV = "969ed193bff4f491b98b52cdbc864956ac394549"

inherit meson

# Add this line to fix the do_unpack warning
S = "${UNPACKDIR}/quickboot-audio-${PV}/quickboot-audio"

# ── Meson options ────────────────────────────────────────────────────────────
# Pass the Yocto-resolved systemd unit directory so meson does not need
# pkg-config auto-detection (which may not work in all cross environments).
EXTRA_OEMESON = "-Dsystemd_system_unit_dir=${systemd_unitdir}/system"

# ── Installed files ──────────────────────────────────────────────────────────
FILES:${PN} = " \
        ${sysconfdir}/modules-load.d/qcom-audio.conf \
        "
