# Keep the initramfs startup-path optimizations in qcom-distro rather than
# patching the oe-core checkout through kas.
FILESEXTRAPATHS:prepend := "${THISDIR}/initramfs-framework:"
SRC_URI:append = " \
    file://0001-initramfs-framework-add-root-only-udev-trigger.patch \
    file://0002-initramfs-framework-support-label-with-root-only-udev-trigger.patch \
    file://0003-initramfs-framework-avoid-external-commands-in-rootfs-resolution.patch \
    file://0004-initramfs-framework-allow-opting-out-of-efivarfs-mount.patch \
    file://0005-initramfs-framework-avoid-processes-when-parsing-cmdline.patch \
    file://0006-initramfs-framework-avoid-external-commands-in-module-loop.patch \
"
