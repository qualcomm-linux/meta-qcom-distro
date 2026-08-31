require qcom-minimal-image.bb

SUMMARY = "Low memory image"

# This image is compatible only with aarch64 (ARMv8)
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

CORE_IMAGE_BASE_INSTALL += " \
    alsa-utils-alsatplg \
    alsa-utils-alsaucm \
    alsa-utils-aplay \
    camera-service \
    camx-dlkm \
    camx-hamoa \
    camx-kodiak \
    camx-lemans \
    camx-nhx \
    camx-talos \
    efivar \
    gstd \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-python \
    gst-plugins-imsdk-prop \
    libcamera \
    libcamera-gst \
    pipewire \
    pipewire-alsa \
    pipewire-modules-meta \
    pipewire-pulse \
    pipewire-spa-tools \
    pipewire-tools \
    pulseaudio-pactl \
    virtual-containerd \
    wireplumber \
"

# IMSDK currently only used and tested on ARMv8 (aarch64) machines.
CORE_IMAGE_BASE_INSTALL:append:aarch64 = " gst-plugins-imsdk-oss"
