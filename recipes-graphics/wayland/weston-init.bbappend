FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " file://weston-terminal.ini"

do_install:append:qcom-distro() {
    cat ${WORKDIR}/sources/weston-terminal.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
}
