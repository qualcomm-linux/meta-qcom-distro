# Enable systemd-grow-rootfs.service to resize root filesystem at boot
FILESEXTRAPATHS:append := "${THISDIR}/${BPN}:"
SRC_URI += "\
    file://growfs-root.conf \
    file://growfs-root.preset \
"

do_install:append:qcom() {
    install -Dm 0644 ${WORKDIR}/sources/growfs-root.preset \
            ${D}${systemd_unitdir}/system-preset/98-growfs-root.preset
    install -Dm 0644 ${WORKDIR}/sources/growfs-root.conf \
            ${D}${systemd_unitdir}/system/systemd-growfs-root.service.d/growfs-root.conf
}

