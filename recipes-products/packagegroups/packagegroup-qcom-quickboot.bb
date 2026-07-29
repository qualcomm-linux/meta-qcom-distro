SUMMARY = "Qualcomm Quickboot packagegroup"
DESCRIPTION = "Package group to bring in quickboot packages"

inherit packagegroup

RDEPENDS:${PN} = "\
    quickboot-audio \
    quickboot-display \
    "
