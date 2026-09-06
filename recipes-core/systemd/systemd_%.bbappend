FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " file://dmaheap-sysusers.conf"

SRC_URI:append:sota = " \
        file://boot.mount-10-no-local-fs-gate.conf \
"

do_install:append:qcom-distro() {
    install -d ${D}${libdir}/sysusers.d
    install -m 0644 ${UNPACKDIR}/dmaheap-sysusers.conf \
        ${D}${libdir}/sysusers.d/dmaheap.conf
}

FILES:${PN}:append:qcom-distro = " ${libdir}/sysusers.d/dmaheap.conf"

do_install:append:sota() {
    install -d ${D}${systemd_system_unitdir}/boot.mount.d
    install -m 0644 ${UNPACKDIR}/boot.mount-10-no-local-fs-gate.conf \
        ${D}${systemd_system_unitdir}/boot.mount.d/10-no-local-fs-gate.conf
}

FILES:${PN}:append:sota = " ${systemd_system_unitdir}/boot.mount.d/10-no-local-fs-gate.conf"
