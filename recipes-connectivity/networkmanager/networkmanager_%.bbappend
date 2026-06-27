# Keep NetworkManager enabled, but avoid auto-enabling wait-online at boot.
do_install:append() {
    if [ -e ${D}${systemd_system_unitdir}/NetworkManager.service ]; then
        sed -i '/^Also=NetworkManager-wait-online\.service[[:space:]]*$/d' \
            ${D}${systemd_system_unitdir}/NetworkManager.service
    fi
}
