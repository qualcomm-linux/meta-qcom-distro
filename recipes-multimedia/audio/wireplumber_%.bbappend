# Enable wireplumber as a system-wide service
SYSTEMD_SERVICE:${PN}:qcom-distro = "wireplumber.service"
SYSTEMD_AUTO_ENABLE:${PN}:qcom-distro = "enable"

FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " \
    file://bluetooth.conf \
"

do_install:append:qcom-distro() {
    install -Dm 0644 ${UNPACKDIR}/bluetooth.conf \
            ${D}${datadir}/wireplumber/wireplumber.conf.d/bluetooth.conf
}

FILES:${PN}:append:qcom-distro = " \
    ${datadir}/wireplumber/wireplumber.conf.d/bluetooth.conf \
"

CONFFILES:${PN}:append:qcom-distro = " \
    ${datadir}/wireplumber/wireplumber.conf.d/bluetooth.conf \
"
