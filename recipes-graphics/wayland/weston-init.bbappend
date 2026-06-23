FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://weston-terminal.ini \
    file://qcom-background.ini \
    file://qcom-background.png \
"

do_install:append:qcom-distro() {
    cat ${S}/weston-terminal.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    cat ${S}/qcom-background.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    install -d ${D}${datadir}/backgrounds
    install -m 0644 ${S}/qcom-background.png \
        ${D}${datadir}/backgrounds/qcom-background.png
}

FILES:${PN}:append:qcom-distro = " ${datadir}/backgrounds/qcom-background.png"
