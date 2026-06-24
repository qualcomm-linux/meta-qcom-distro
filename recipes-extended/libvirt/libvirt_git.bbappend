# QEMU 11.0.0+ requires 64-bit host architecture
# https://lists.yoctoproject.org/g/meta-virtualization/message/9893
PACKAGECONFIG:remove:qcom-distro:arm = "qemu"
