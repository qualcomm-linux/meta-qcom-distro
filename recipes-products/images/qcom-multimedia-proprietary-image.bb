require qcom-multimedia-image.bb

SUMMARY = "An image built on top of multimedia image for proprietary features"

# Use proprietary diag-router instead of open-source diag
PREFERRED_PROVIDER_virtual-diag-router = "diag-router"

# This image is compatible only with aarch64 (ARMv8)
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

CORE_IMAGE_BASE_INSTALL += " \
    camx-dlkm \
    iris-video-dlkm \
    kgsl-dlkm \
    qcom-adreno \
    virtual-diag-router \
"
CORE_IMAGE_BASE_INSTALL:append = " \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-audioreach', ' packagegroup-audioreach', '', d)} \
"
