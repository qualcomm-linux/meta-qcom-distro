SUMMARY = "Qualcomm proprietary camera features"
DESCRIPTION = "Packagegroup containing proprietary Qualcomm camera features \
and supporting components for Qualcomm platforms."

inherit packagegroup

# Compatible only with AArch64 targets
COMPATIBLE_HOST = "aarch64.*-linux"

RDEPENDS:${PN} = "\
    camera-service \
    camx-dlkm \
    camx-hamoa \
    camx-kodiak \
    camx-lemans \
    camx-nhx \
    camx-nord \
    camx-shikra \
    camx-talos \
    gst-plugins-imsdk-prop \
    "
