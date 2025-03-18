require qcom-minimal-image.bb

LICENSE = "MIT"

DESCRIPTION = "An XFCE demo image for Qualcomm machines"
SUMMARY = "An XFCE demo image."

export IMAGE_BASENAME = "Qualcomm-XFCE-demo-image"

IMAGE_FEATURES += "x11"
REQUIRED_DISTRO_FEATURES += "x11 opengl"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-qcom-distro-xfce \
    \
    packagegroup-qcom-utilities \
    \
    alsa-utils \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    ${@bb.utils.contains("LICENSE_FLAGS_ACCEPTED", "commercial", "gstreamer1.0-plugins-bad", "", d)} \
    ${@bb.utils.contains("LICENSE_FLAGS_ACCEPTED", "commercial", "gstreamer1.0-libav", "", d)} \
    libcamera \
    libcamera-gst \
    pipewire \
    v4l-utils \
    yavta \
"

SYSTEMD_DEFAULT_TARGET = "graphical.target"
