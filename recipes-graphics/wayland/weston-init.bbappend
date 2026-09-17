FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://weston-terminal.ini \
    file://qcom-background.ini \
    file://qcom-background.png \
    file://weston-groups.conf \
    file://50-networkmanager-weston.rules \
"

do_install:append:qcom-distro() {
    cat ${S}/weston-terminal.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    cat ${S}/qcom-background.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    install -d ${D}${datadir}/backgrounds
    install -m 0644 ${S}/qcom-background.png \
        ${D}${datadir}/backgrounds/qcom-background.png
    install -d ${D}${datadir}/polkit-1/rules.d
    install -m 0644 ${S}/50-networkmanager-weston.rules \
        ${D}${datadir}/polkit-1/rules.d/50-networkmanager-weston.rules
    install -d ${D}${systemd_system_unitdir}/weston.service.d
    install -m 0644 ${S}/weston-groups.conf \
        ${D}${systemd_system_unitdir}/weston.service.d/weston-groups.conf
}

FILES:${PN}:append:qcom-distro = " \
    ${datadir}/backgrounds/qcom-background.png \
    ${datadir}/polkit-1/rules.d/50-networkmanager-weston.rules \
    ${systemd_unitdir}/system/weston.service.d/weston-groups.conf \
"
