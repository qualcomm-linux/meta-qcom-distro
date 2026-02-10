FILESEXTRAPATHS:append := "${THISDIR}/${BPN}:"

SRC_URI += "\
    file://disable-wait-online.conf \
"

do_install:append:qcom() {
    install -d ${D}${systemd_unitdir}/system/systemd-networkd-wait-online.service.d
    install -Dm 0644 ${WORKDIR}/sources/disable-wait-online.conf \
            ${D}${systemd_unitdir}/system/systemd-networkd-wait-online.service.d
}
