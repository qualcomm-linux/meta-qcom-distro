# Recipe for early camera boot optimizations and preview application.
# Installs systemd services, udev rules, kernel module load lists,
# and helper scripts to achieve fast time-to-first-frame on boot.

SUMMARY = "Early Camera Boot Optimizations"
DESCRIPTION = "Installs udev rules, kernel module load list, cam-server \
systemd unit, and setup scripts to bring up the camera pipeline early in \
the boot sequence."

SRC_URI = "git://github.com/qualcomm/quickboot.git;protocol=https;branch=main"
SRCREV = "969ed193bff4f491b98b52cdbc864956ac394549"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=2998c54c288b081076c9af987bdf4838"

inherit meson systemd

S = "${UNPACKDIR}/quickboot-camera-${PV}/quickboot-camera"

# ── Meson options ────────────────────────────────────────────────────────────
# Pass the Yocto-resolved systemd unit directory so meson does not need
# pkg-config auto-detection (which may not work in all cross environments).
EXTRA_OEMESON = "-Dsystemd_system_unit_dir=${systemd_unitdir}/system"

FILES:${PN} += " \
    ${sysconfdir}/modules-load.d/qcom-camera.conf\
    ${sysconfdir}/udev/rules.d/02-cam-server.rules \
    ${sysconfdir}/systemd/system/cam-server.service \
    ${bindir}/camx-set-vendor-dtbo.sh \
    ${bindir}/camera-sensors-prune.sh \
    "

SYSTEMD_SERVICE:${PN} = "cam-server.service"

RDEPENDS:${PN} += "udev"
