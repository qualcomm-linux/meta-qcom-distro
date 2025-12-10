inherit systemd

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

RRECOMMENDS:${PN} += " \
    glibc-gconv-utf-16 \
    glibc-gconv-utf-32 \
"

SRC_URI:append:qcom = " file://0001-Use-system-bus-instead-of-session-for-obexd.patch \
"

#Include obex to support obex related profiles like OPP, FTP, MAP, PBAP
RDEPENDS:${PN} += "${PN}-obex"

#Include only desired tools that are conditional on READLINE in bluez
INST_TOOLS_READLINE = " \
    tools/obexctl \
"

#Remove desired tools from noinst-tools
NOINST_TOOLS:remove = " \
    ${@bb.utils.contains('PACKAGECONFIG', 'readline', '${INST_TOOLS_READLINE}', '', d)} \
"

do_install:append:qcom() {
    for f in ${INST_TOOLS_READLINE} ; do
        install -m 755 ${B}/$f ${D}/${bindir}
    done

    install -m 0644 ${B}/obexd/src/obex.service ${D}${systemd_system_unitdir}/obex.service
}

SYSTEMD_PACKAGES += "${PN}-obex"
SYSTEMD_SERVICE:${PN}-obex += "obex.service"
