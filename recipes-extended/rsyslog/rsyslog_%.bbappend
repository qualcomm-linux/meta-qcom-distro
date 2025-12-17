FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " file://rsyslog.logrotate.qcom"

do_install:append:qcom-distro() {
    install -Dm 0644 ${UNPACKDIR}/rsyslog.logrotate.qcom \
            ${D}${sysconfdir}/logrotate.d/logrotate.rsyslog
}
