FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://20-no-acd.conf"

do_install:append() {
    install -d ${D}${sysconfdir}/NetworkManager/conf.d
    install -m 0644 ${UNPACKDIR}/20-no-acd.conf ${D}${sysconfdir}/NetworkManager/conf.d/20-no-acd.conf
}
