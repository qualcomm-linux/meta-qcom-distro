SUMMARY = "Qualcomm camera subsystem packagegroup"
DESCRIPTION = "Packagegroup containing camera stack components and utilities \
for camera subsystem bring-up on Qualcomm platforms"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "\
    camera-service \
    camx-dlkm \
    camx-hamoa \
    camx-kodiak \
    camx-lemans \
    camx-nhx \
    camx-talos \
    efivar \
    gst-plugins-imsdk-oss \
    gst-plugins-imsdk-prop \
    gstd \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-python \
    libcamera \
    libcamera-gst \
    "
