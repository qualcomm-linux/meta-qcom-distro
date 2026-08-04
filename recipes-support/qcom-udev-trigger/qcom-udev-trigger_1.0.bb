SUMMARY = "Split udev coldplug into early block and deferred non-block passes"
DESCRIPTION = "Installs a systemd-udev-trigger.service drop-in that triggers \
block devices during sysinit, plus a deferred service that coldplugs non-block \
devices after basic.target."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://systemd-udev-trigger-block-only.conf \
    file://qcom-udev-trigger-nonblock.service \
"

inherit allarch features_check systemd
REQUIRED_DISTRO_FEATURES = "systemd"

S = "${UNPACKDIR}"

SYSTEMD_SERVICE:${PN} = "qcom-udev-trigger-nonblock.service"

do_compile[noexec] = "1"

do_install() {
    install -Dm 0644 ${UNPACKDIR}/systemd-udev-trigger-block-only.conf \
        ${D}${systemd_system_unitdir}/systemd-udev-trigger.service.d/block-only.conf
    install -Dm 0644 ${UNPACKDIR}/qcom-udev-trigger-nonblock.service \
        ${D}${systemd_system_unitdir}/qcom-udev-trigger-nonblock.service
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/systemd-udev-trigger.service.d/block-only.conf \
"
