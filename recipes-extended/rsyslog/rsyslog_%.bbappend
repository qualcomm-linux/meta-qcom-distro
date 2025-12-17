FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://rsyslog.logrotate.qcom"

do_install:append:qcom() {
    install -Dm 0644 ${UNPACKDIR}/rsyslog.logrotate.qcom \
            ${D}${sysconfdir}/logrotate.d/logrotate.rsyslog
}
