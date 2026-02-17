FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-Use-system-bus-instead-of-session-for-obexd.patch \
"

# UTF-16/32 conversion support for OBEX Bluetooth file transfers
RDEPENDS:${PN}:append:libc-glibc:qcom-distro = " \
    glibc-gconv-utf-16 \
    glibc-gconv-utf-32 \
"

SYSTEMD_PACKAGES:append:qcom-distro = " ${PN}-obex"

do_install:append:qcom-distro() {
    install -m 0644 ${B}/obexd/src/obex.service ${D}${systemd_system_unitdir}/obex.service
}
