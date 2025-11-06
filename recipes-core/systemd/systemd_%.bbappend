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

# This disables the 'mac' policy for pni-names
# We do not want MAC address based naming, for example the wifi on RB1
# gets a new MAC address every boot *and* doesn't support any of the
# naming features. This leads to a new, unpredictable interface name on
# every boot

do_install:append:qcom() {
	sed -i -e 's: mac::g' ${D}${nonarch_libdir}/systemd/network/99-default.link
} 
