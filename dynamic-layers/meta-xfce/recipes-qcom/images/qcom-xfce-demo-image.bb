DESCRIPTION = "An XFCE demo image."

# For ease of development, include an SSH server as well as package management
IMAGE_FEATURES += "x11 package-management ssh-server-dropbear"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-qcom-xfce \
    \
    kernel-modules \
    linux-firmware \
    \
    packagegroup-qcom-console-utils \
    \
    alsa-utils v4l-utils yavta \
    libcamera libcamera-gst gstreamer1.0-plugins-base gstreamer1.0-libav gstreamer1.0-plugins-good gstreamer1.0-plugins-bad \
    pipewire \
"

inherit features_check
REQUIRED_DISTRO_FEATURES = "x11 opengl"

LICENSE = "MIT"

export IMAGE_BASENAME = "Qualcomm-XFCE-demo-image"

SYSTEMD_DEFAULT_TARGET = "graphical.target"
