FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " file://disable-powerkey.conf"

do_install:append:qcom-distro() {
   install -Dm 0644 ${UNPACKDIR}/disable-powerkey.conf \
           ${D}${systemd_unitdir}/logind.conf.d/disable-powerkey.conf
}
