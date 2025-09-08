require qcom-multimedia-image.bb

SUMMARY = "An image built on top of multimedia image for proprietary features"

CORE_IMAGE_BASE_INSTALL += " \
    iris-video-dlkm \
    kgsl-dlkm \
    qcom-adreno \
"

