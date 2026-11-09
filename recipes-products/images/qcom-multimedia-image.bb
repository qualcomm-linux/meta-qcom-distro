require qcom-console-image.bb

SUMMARY = "Basic Wayland image with Weston"

IMAGE_FEATURES += "weston"

CORE_IMAGE_BASE_INSTALL += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'weston-xwayland xterm', '', d)} \
    alsa-utils-alsatplg \
    alsa-utils-alsaucm \
    alsa-utils-aplay \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'docker-compose', '', d)} \
    gstd \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-python \
    libcamera \
    libcamera-gst \
    libdrm-tests \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'packagegroup-container', '', d)} \
    packagegroup-qcom-benchmark \
    packagegroup-qcom-quickboot \
    packagegroup-qcom-quickboot-tests \
    packagegroup-qcom-test-pkgs \
    packagegroup-qcom-utilities-gpu-utils \
    pipewire \
    pipewire-alsa \
    pipewire-modules-meta \
    pipewire-pulse \
    pipewire-spa-tools \
    pipewire-tools \
    pulseaudio-pactl \
    quickboot-camera-camss \
    tensorflow-lite-tools \
    thermald \
    userspace-resource-manager \
    weston \
    weston-examples \
    weston-init \
    wireplumber \
"

# IMSDK and the CAMSS camera test recipes are limited to ARMv8 (aarch64).
CORE_IMAGE_BASE_INSTALL:append:aarch64 = " \
    gst-plugins-imsdk-oss \
    gst-plugins-imsdk-python \
    quickboot-camera-preview-camss \
    quickboot-camera-readiness-camss \
"

# let's make sure we have a good image.
REQUIRED_DISTRO_FEATURES += "wayland"
