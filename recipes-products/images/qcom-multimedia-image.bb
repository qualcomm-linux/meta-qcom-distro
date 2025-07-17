require qcom-console-image.bb

SUMMARY = "Basic Wayland image with Weston"

IMAGE_FEATURES += "weston"

CORE_IMAGE_BASE_INSTALL += " \
    packagegroup-qcom-test-pkgs \
    packagegroup-qcom-utilities-gpu-utils \
    weston \
    weston-examples \
    weston-init \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'weston-xwayland xterm', '', d)} \
    alsa-utils-aplay \
    alsa-utils-alsaucm \
    alsa-utils-alsatplg \
    pipewire \
    pipewire-pulse \
    pipewire-alsa \
    pipewire-spa-tools \
    pipewire-modules-meta \
    pipewire-tools \
    wireplumber \
"

# let's make sure we have a good image.
REQUIRED_DISTRO_FEATURES += "wayland"
