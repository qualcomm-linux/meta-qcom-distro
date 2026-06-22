FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://weston-terminal.ini \
    file://qcom-background.ini \
    file://qcom-background.png \
"

do_install:append:qcom-distro() {
    cat ${WORKDIR}/sources/weston-terminal.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    cat ${WORKDIR}/sources/qcom-background.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    install -d ${D}${datadir}/backgrounds
    install -m 0644 ${WORKDIR}/sources/qcom-background.png \
        ${D}${datadir}/backgrounds/qcom-background.png
}

FILES:${PN} += "${datadir}/backgrounds/qcom-background.png"
CONFFILES:${PN} += "${sysconfdir}/xdg/weston/weston.ini"
