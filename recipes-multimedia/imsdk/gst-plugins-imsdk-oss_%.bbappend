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

pkg_prerm:${PN}-apps:qcom-distro() {
    weston_config="$D${sysconfdir}/xdg/weston/weston.ini"

    if [ -f "$weston_config" ]; then
        sed -i '
            /^\[launcher\]$/ {
                N
                /\nicon=\/usr\/share\/qdemo\/Qdemo\.png$/ {
                    N
                    /\npath=\/usr\/bin\/Qdemo$/d
                }
            }
            P
            D
        ' "$weston_config"
    fi
}
