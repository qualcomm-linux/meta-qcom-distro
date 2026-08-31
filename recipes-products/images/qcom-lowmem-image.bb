require qcom-minimal-image.bb

SUMMARY = "Low memory image with camera and audio support"
DESCRIPTION = "A low-memory-footprint image based on qcom-minimal-image, \
extended with camera (CamX/libcamera) and audio (ALSA/PipeWire/GStreamer) \
support for memory-constrained targets."

# This image is compatible only with aarch64 (ARMv8)
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

CORE_IMAGE_BASE_INSTALL:append = " \
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
