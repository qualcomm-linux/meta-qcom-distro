SUMMARY = "Qualcomm QuickBoot early subsystem bring-up package group"
DESCRIPTION = "Package group containing QuickBoot optimizations for early \
audio and display bring-up during system boot"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "\
    quickboot-audio \
    quickboot-display \
    "
