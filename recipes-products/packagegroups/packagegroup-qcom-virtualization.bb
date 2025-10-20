SUMMARY = "Qualcomm virtualization packagegroup"
DESCRIPTION = "Package group to bring in packages required for virtualization"

inherit packagegroup

RDEPENDS:${PN} += " \
    libvirt \
    libvirt-libvirtd \
    libvirt-virsh \
    qemu \
    "
