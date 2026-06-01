require qcom-multimedia-proprietary-image.bb

SUMMARY = "Image Built on top of multimedia proprietary image to support browser and demos"

EXCLUDE_FROM_WORLD = "1"

CORE_IMAGE_BASE_INSTALL += " \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'chromium-browser-layer', 'chromium-ozone-wayland', '', d)} \
"
