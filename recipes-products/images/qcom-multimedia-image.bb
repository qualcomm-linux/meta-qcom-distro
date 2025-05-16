require qcom-console-image.bb

SUMMARY = "Basic Wayland image with Weston"

# let's make sure we have a good image.
REQUIRED_DISTRO_FEATURES += "wayland"
