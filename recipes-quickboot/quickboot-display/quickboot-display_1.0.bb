# Recipe for early display boot optimizations.
#
# Installs custom Weston service configurations, udev rules for
# immediate triggering, and kernel module load lists to achieve
# early display availability.

SUMMARY = "Early Display Boot Optimizations"
DESCRIPTION = "Installs udev rules, kernel module load list, and Weston \
systemd units to bring up the display pipeline early in the boot sequence, \
reducing time-to-first-frame"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=2998c54c288b081076c9af987bdf4838"

SRC_URI = "git://github.com/qualcomm/quickboot.git;protocol=https;branch=main"
SRCREV = "969ed193bff4f491b98b52cdbc864956ac394549"

inherit meson systemd

# Add this line to fix the do_unpack warning
S = "${UNPACKDIR}/quickboot-display-${PV}/quickboot-display"

# ── Meson options ────────────────────────────────────────────────────────────
# Pass the Yocto-resolved systemd unit directory so meson does not need
# pkg-config auto-detection (which may not work in all cross environments).
EXTRA_OEMESON = "-Dsystemd_system_unit_dir=${systemd_unitdir}/system"

# ── Installed files ──────────────────────────────────────────────────────────
FILES:${PN} = " \
    ${sysconfdir}/modules-load.d/qcom-display.conf \
    ${sysconfdir}/udev/rules.d/03-drm.rules \
    ${sysconfdir}/systemd/system/weston.socket \
    ${sysconfdir}/systemd/system/weston.service \
"
SYSTEMD_SERVICE:${PN} = "weston.service"

RDEPENDS:${PN} += "udev"
