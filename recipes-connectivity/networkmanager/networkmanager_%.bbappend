FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://nm-wifi-vif-create.sh \
    file://nm-wifi-vif-create.service \
"

do_install:append:qcom-distro() {
    install -d ${D}${libexecdir}
    install -m 0755 ${UNPACKDIR}/nm-wifi-vif-create.sh \
        ${D}${libexecdir}/nm-wifi-vif-create.sh

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${UNPACKDIR}/nm-wifi-vif-create.service \
        ${D}${systemd_system_unitdir}/nm-wifi-vif-create.service
}

FILES:${PN}:append:qcom-distro = " \
    ${libexecdir}/nm-wifi-vif-create.sh \
    ${systemd_system_unitdir}/nm-wifi-vif-create.service \
"
