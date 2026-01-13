require qcom-multimedia-image.bb

SUMMARY = "An image built on top of multimedia image for proprietary features"

# This image is compatible only with aarch64 (ARMv8)
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

CORE_IMAGE_BASE_INSTALL += " \
    camx-dlkm \
    iris-video-dlkm \
    kgsl-dlkm \
    qcom-adreno \
    qcom-sensors-binaries \
"
CORE_IMAGE_BASE_INSTALL:append = " \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-audioreach', ' packagegroup-audioreach', '', d)} \
"
