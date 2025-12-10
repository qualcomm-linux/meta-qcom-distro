FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append = " \
    file://0001-Use-system-bus-instead-of-session-for-obexd.patch \
    file://obexd-tmpfiles.conf \
"

# UTF-16/32 conversion support for OBEX Bluetooth file transfers
RRECOMMENDS:${PN} += " \
    glibc-gconv-utf-16 \
    glibc-gconv-utf-32 \
"

SYSTEMD_PACKAGES += "${PN}-obex"

FILES:${PN}-obex += "${libdir}/tmpfiles.d/obexd.conf"

do_install:append:qcom-distro() {
    install -d ${D}${libdir}/tmpfiles.d
    install -m 0644 ${UNPACKDIR}/obexd-tmpfiles.conf ${D}${libdir}/tmpfiles.d/obexd.conf

    install -m 0644 ${B}/obexd/src/obex.service ${D}${systemd_system_unitdir}/obex.service
}
