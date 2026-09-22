FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://weston-terminal.ini \
    file://qcom-background.ini \
    file://qcom-background.png \
    file://weston-groups.conf \
    file://weston-sysusers.conf \
    file://weston-tmpfiles.conf \
"

do_install:append:qcom-distro() {
    cat ${S}/weston-terminal.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    cat ${S}/qcom-background.ini >> ${D}${sysconfdir}/xdg/weston/weston.ini
    install -d ${D}${datadir}/backgrounds
    install -m 0644 ${S}/qcom-background.png \
        ${D}${datadir}/backgrounds/qcom-background.png
    install -d ${D}${systemd_system_unitdir}/weston.service.d
    install -m 0644 ${S}/weston-groups.conf \
        ${D}${systemd_system_unitdir}/weston.service.d/weston-groups.conf

    install -d ${D}${nonarch_libdir}/sysusers.d
    install -m 0644 ${S}/weston-sysusers.conf \
        ${D}${nonarch_libdir}/sysusers.d/weston.conf

    install -d ${D}${nonarch_libdir}/tmpfiles.d
    install -m 0644 ${S}/weston-tmpfiles.conf \
        ${D}${nonarch_libdir}/tmpfiles.d/weston.conf
}

FILES:${PN}:append:qcom-distro = " \
    ${datadir}/backgrounds/qcom-background.png \
    ${systemd_unitdir}/system/weston.service.d/weston-groups.conf \
    ${nonarch_libdir}/sysusers.d/weston.conf \
    ${nonarch_libdir}/tmpfiles.d/weston.conf \
"
