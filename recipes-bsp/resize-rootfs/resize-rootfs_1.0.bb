SUMMARY = "Systemd drop-in service fragment to resize root filesystem at first boot"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

SRC_URI += "\
    file://growfs-root.conf \
    file://growfs-root.preset \
"

inherit features_check systemd
REQUIRED_DISTRO_FEATURES = "systemd"

do_install:append:qcom() {
    install -Dm 0644 ${WORKDIR}/sources/growfs-root.preset \
            ${D}${systemd_unitdir}/system-preset/98-growfs-root.preset
    install -Dm 0644 ${WORKDIR}/sources/growfs-root.conf \
            ${D}${systemd_unitdir}/system/systemd-growfs-root.service.d/growfs-root.conf
}

PACKAGES = "${PN}"

FILES:${PN} += "${systemd_unitdir}"

RDEPENDS:${PN} += "e2fsprogs-resize2fs"
