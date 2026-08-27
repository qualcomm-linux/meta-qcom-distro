FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro:sota = " \
    file://10-use-prebuilt-ldconfig-cache.conf \
    file://qcom-ldconfig-update \
"

do_install:append:qcom-distro:sota() {
    install -Dm 0644 \
        ${UNPACKDIR}/10-use-prebuilt-ldconfig-cache.conf \
        ${D}${systemd_system_unitdir}/ldconfig.service.d/10-use-prebuilt-ldconfig-cache.conf
    install -Dm 0755 \
        ${UNPACKDIR}/qcom-ldconfig-update \
        ${D}${libexecdir}/qcom-ldconfig-update
}

FILES:${PN}:append:qcom-distro:sota = " \
    ${systemd_system_unitdir}/ldconfig.service.d/10-use-prebuilt-ldconfig-cache.conf \
    ${libexecdir}/qcom-ldconfig-update \
"
