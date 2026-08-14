FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " file://dmaheap.conf"
SRC_URI:append:qcom-distro = " file://pipewire-telephony-dbus.conf"

do_install:append:qcom-distro() {
    install -d ${D}${systemd_system_unitdir}/pipewire.service.d
    install -m 0644 ${UNPACKDIR}/dmaheap.conf \
        ${D}${systemd_system_unitdir}/pipewire.service.d/dmaheap.conf
    install -Dm 0644 ${UNPACKDIR}/pipewire-telephony-dbus.conf \
        ${D}${sysconfdir}/dbus-1/system.d/pipewire-telephony-dbus.conf
}

FILES:${PN}:append:qcom-distro = " ${sysconfdir}/dbus-1/system.d/pipewire-telephony-dbus.conf"
CONFFILES:${PN}:append:qcom-distro = " ${sysconfdir}/dbus-1/system.d/pipewire-telephony-dbus.conf"

# Enable pipewire-pulse as a system-wide service
SYSTEMD_SERVICE:${PN}-pulse:qcom-distro = "pipewire-pulse.service"
SYSTEMD_AUTO_ENABLE:${PN}-pulse:qcom-distro = "enable"
SYSTEMD_PACKAGES:append:qcom-distro = " ${PN}-pulse"

FILES:${PN}-pulse:append:qcom-distro = " ${systemd_unitdir}/system-preset/98-pipewire-pulse.preset"
