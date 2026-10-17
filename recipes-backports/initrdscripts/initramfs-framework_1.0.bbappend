FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/files:"
SRC_URI:append:qcom-distro = " \
    file://0001-initramfs-framework-add-root-only-udev-trigger.patch \
    file://0002-initramfs-framework-support-label-with-root-only-udev-trigger.patch \
    file://0003-initramfs-framework-avoid-external-commands-in-rootfs-resolution.patch \
"
