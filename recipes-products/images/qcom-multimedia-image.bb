require qcom-console-image.bb

SUMMARY = "Basic Wayland image with Weston"

CORE_IMAGE_BASE_INSTALL += " \
    packagegroup-qcom-utilities-gpu-utils \
"

# let's make sure we have a good image.
REQUIRED_DISTRO_FEATURES += "wayland"
