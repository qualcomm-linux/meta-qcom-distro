FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " file://weston-qdemo-launcher.ini"

do_install:append:qcom-distro() {
    install -d ${D}${datadir}/qdemo
    install -m 0644 ${UNPACKDIR}/weston-qdemo-launcher.ini ${D}${datadir}/qdemo/
}

FILES:${PN}-apps:append:qcom-distro = " ${datadir}/qdemo/weston-qdemo-launcher.ini"

# The launcher entry is appended to weston.ini, which weston-init installs:
# make sure weston-init is installed first.
RDEPENDS:${PN}-apps:append:qcom-distro = " weston-init"

pkg_postinst:${PN}-apps:qcom-distro() {
    cat $D${datadir}/qdemo/weston-qdemo-launcher.ini >> $D${sysconfdir}/xdg/weston/weston.ini
}

pkg_postrm_ontarget:${PN}-apps:qcom-distro() {
    if [ -f /etc/xdg/weston/weston.ini ]; then
        sed -i '/^$/{N;N;N; /^\n\[launcher\]\nicon=\/usr\/share\/qdemo\/Qdemo.png\npath=\/usr\/bin\/Qdemo$/d}' /etc/xdg/weston/weston.ini
    fi
}
