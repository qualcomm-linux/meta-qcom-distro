FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " file://weston-qdemo-launcher.ini"

do_install:append:qcom-distro() {
    install -d ${D}${datadir}/qdemo
    install -m 0644 ${UNPACKDIR}/weston-qdemo-launcher.ini ${D}${datadir}/qdemo/
}

FILES:${PN}-apps:append:qcom-distro = " ${datadir}/qdemo/weston-qdemo-launcher.ini"
RDEPENDS:${PN}-apps:append:qcom-distro = " weston-init"

pkg_postinst:${PN}-apps:qcom-distro() {
    weston_config="$D${sysconfdir}/xdg/weston/weston.ini"
    qdemo_launcher="$D${datadir}/qdemo/weston-qdemo-launcher.ini"

    if [ ! -f "$weston_config" ]; then
        echo "QDemo launcher cannot update missing $weston_config" >&2
        exit 1
    fi

    if ! grep -Fqx 'path=/usr/bin/Qdemo' "$weston_config"; then
        printf '\n' >> "$weston_config"
        cat "$qdemo_launcher" >> "$weston_config"
    fi
}

pkg_postrm_ontarget:${PN}-apps:qcom-distro() {
    if [ -f /etc/xdg/weston/weston.ini ]; then
        sed -i '/^$/{N;N;N; /^\n\[launcher\]\nicon=\/usr\/share\/qdemo\/Qdemo.png\npath=\/usr\/bin\/Qdemo$/d}' /etc/xdg/weston/weston.ini
    fi
}
