FILESEXTRAPATHS:append := "${THISDIR}/${BPN}:"

SRC_URI += "\
    file://override-timeout.conf \
"

do_install:append:qcom() {
    install -d ${D}${systemd_unitdir}/system/systemd-networkd-wait-online.service.d
    install -Dm 0644 ${WORKDIR}/sources/override-timeout.conf \
            ${D}${systemd_unitdir}/system/systemd-networkd-wait-online.service.d
}
