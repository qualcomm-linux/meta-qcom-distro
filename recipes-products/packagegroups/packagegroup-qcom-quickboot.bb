SUMMARY = "Qualcomm QuickBoot early subsystem bring-up package group"
DESCRIPTION = "Package group containing QuickBoot optimizations for early display, \
audio, and camera bring-up during system boot"

inherit packagegroup

RDEPENDS:${PN} = "\
    quickboot-audio \
    quickboot-camera \
    quickboot-display \
    "
