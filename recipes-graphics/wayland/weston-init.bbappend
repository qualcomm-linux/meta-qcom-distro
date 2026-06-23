FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://weston-terminal.ini \
    file://weston.service.d/display-ready.conf \
    file://99-drm-systemd.rules \
"

do_install:append:qcom-distro() {
    cat ${WORKDIR}/sources/weston-terminal.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    install -d ${D}${systemd_system_unitdir}/weston.service.d
    install -m 0644 ${WORKDIR}/sources/weston.service.d/display-ready.conf \
        ${D}${systemd_system_unitdir}/weston.service.d/display-ready.conf
    install -d ${D}${nonarch_base_libdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/sources/99-drm-systemd.rules \
        ${D}${nonarch_base_libdir}/udev/rules.d/99-drm-systemd.rules
}

FILES:${PN}:append:qcom-distro = " ${systemd_system_unitdir}/weston.service.d/display-ready.conf"
FILES:${PN}:append:qcom-distro = " ${nonarch_base_libdir}/udev/rules.d/99-drm-systemd.rules"
