require qcom-multimedia-image.bb

SUMMARY = "Feature rich Weston Wayland image with support for browser and other demos"

EXCLUDE_FROM_WORLD = "1"

CORE_IMAGE_BASE_INSTALL += " \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'chromium-browser-layer', 'chromium-ozone-wayland', '', d)} \
"
