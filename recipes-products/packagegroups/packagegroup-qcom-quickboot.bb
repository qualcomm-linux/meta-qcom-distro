SUMMARY = "Qualcomm Quickboot packagegroup"
DESCRIPTION = "Package group to bring in quickboot packages"

inherit packagegroup

RDEPENDS:${PN} = "\
    qcom-audio-chime \
    qcom-camera-preview \
    quickboot-audio \
    quickboot-camera \
    "
