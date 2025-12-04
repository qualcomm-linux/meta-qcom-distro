SUMMARY = "Boot-time BDADDR setup for Qualcomm QCA Bluetooth device"
DESCRIPTION = "Systemd service and script to generate Device Address (BDADDR)  \
for unconfigured Qualcomm QCA BT SoCs and program it using btmgmt at every boot."

inherit systemd

SRC_URI = " \
    file://qca_set_bdaddr.service \
    file://qca_set_bdaddr.sh \
"

S = "${UNPACKDIR}"

SYSTEMD_SERVICE:${PN} = "qca_set_bdaddr.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += " \
    ${systemd_unitdir}/system/qca_set_bdaddr.service \
    ${bindir}/qca_set_bdaddr.sh \
"

do_install() {
    # Install systemd unit
    install -D -m 0644 ${S}/qca_set_bdaddr.service \
        ${D}${systemd_unitdir}/system/qca_set_bdaddr.service

    # Install script
    install -D -m 0755 ${S}/qca_set_bdaddr.sh \
        ${D}${bindir}/qca_set_bdaddr.sh
}

RDEPENDS:${PN} += " \
    bluez5-noinst-tools \
"
