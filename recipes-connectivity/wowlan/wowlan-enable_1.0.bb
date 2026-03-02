SUMMARY = "Enable WoWLAN magic packet at boot"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

inherit systemd

SRC_URI = "file://wowlan-enable.service"

SYSTEMD_SERVICE:${PN} = "wowlan-enable.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/sources/wowlan-enable.service \
        ${D}${systemd_system_unitdir}
}

