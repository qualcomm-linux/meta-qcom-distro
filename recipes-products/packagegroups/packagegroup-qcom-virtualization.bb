SUMMARY = "Qualcomm virtualization packagegroup"
DESCRIPTION = "Package group to bring in packages required for virtualization"

inherit packagegroup

RDEPENDS:${PN} += " \
    libvirt \
    libvirt-libvirtd \
    libvirt-virsh \
    "

# qemu 11.0.0 only supports 64-bit hosts
RDEPENDS:${PN}:append:aarch64 = " \
    qemu \
    "
