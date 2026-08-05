FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " file://dmaheap-sysusers.conf"

do_install:append:qcom-distro() {
    install -d ${D}${libdir}/sysusers.d
    install -m 0644 ${UNPACKDIR}/dmaheap-sysusers.conf \
        ${D}${libdir}/sysusers.d/dmaheap.conf
}

FILES:${PN}:append:qcom-distro = " ${libdir}/sysusers.d/dmaheap.conf"
