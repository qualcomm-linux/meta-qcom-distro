SUMMARY = "Camera stack packagegroup"
DESCRIPTION = "Packagegroup containing camera framework and multimedia \
components required for camera capture and streaming."

inherit packagegroup

RDEPENDS:${PN} = "\
    efivar \
    gstd \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-python \
    libcamera \
    libcamera-gst \
    "

RDEPENDS:${PN}:append:aarch64 = " \
    gst-plugins-imsdk-oss \
    "
