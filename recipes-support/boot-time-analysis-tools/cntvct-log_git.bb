SUMMARY = "cntvct-log - ARM virtual counter boot time logger"
DESCRIPTION = "Userspace tool to log ARM CNTVCT_EL0 counter values for boot time analysis"
HOMEPAGE = "https://gitlab.com/CentOS/automotive/src/boot-time-analysis-tools"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=1c2e0cc0dec0b709fe547806b55737b0"

SRC_URI = "git://gitlab.com/CentOS/automotive/src/boot-time-analysis-tools.git;protocol=https;branch=main"
SRCREV = "2409ce37e7389d8079bdb8740cb6a44b505fb8d4"

# Point S to the cntvct-log subdirectory (subdirectory override is allowed in Scarthgap+)
#S = "${UNPACKDIR}/git/cntvct-log"
S = "${UNPACKDIR}/cntvct-log-git/cntvct-log"

inherit meson pkgconfig systemd

# Manually install service file if meson.build doesn't handle it
do_install:append() {
    if [ -f ${S}/usr/lib/systemd/system/cntvct@.service ]; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${S}/usr/lib/systemd/system/cntvct@.service \
            ${D}${systemd_system_unitdir}/cntvct@.service
    fi
}

SYSTEMD_SERVICE:${PN} = "cntvct@.service"
SYSTEMD_AUTO_ENABLE = "disable"

# Explicitly include the service file in the package
FILES:${PN} += "${systemd_system_unitdir}/cntvct@.service"
