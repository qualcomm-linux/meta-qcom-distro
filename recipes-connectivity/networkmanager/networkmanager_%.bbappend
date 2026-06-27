FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = "file://override.conf"

do_install:append:qcom-distro() {
        install -d ${D}${systemd_system_unitdir}/NetworkManager-wait-online.service.d
        install -m 0644 ${WORKDIR}/sources/override.conf \
            ${D}${systemd_system_unitdir}/NetworkManager-wait-online.service.d/override.conf
}
