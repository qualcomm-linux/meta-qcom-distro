require qcom-console-image.bb

SUMMARY = "Basic Wayland image with Weston"

IMAGE_FEATURES += "weston"

CORE_IMAGE_BASE_INSTALL += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'weston-xwayland xterm', '', d)} \
    alsa-utils-alsatplg \
    alsa-utils-alsaucm \
    alsa-utils-aplay \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    packagegroup-qcom-test-pkgs \
    packagegroup-qcom-utilities-gpu-utils \
    pipewire \
    pipewire-alsa \
    pipewire-modules-meta \
    pipewire-pulse \
    pipewire-spa-tools \
    pipewire-tools \
    weston \
    weston-examples \
    weston-init \
    wireplumber \
    libcamera \
"

# let's make sure we have a good image.
REQUIRED_DISTRO_FEATURES += "wayland"
