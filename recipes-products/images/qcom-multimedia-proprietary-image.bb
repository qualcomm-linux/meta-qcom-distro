require qcom-multimedia-image.bb

SUMMARY = "An image built on top of multimedia image for proprietary features"

# This image is compatible only with aarch64 (ARMv8)
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

CORE_IMAGE_BASE_INSTALL += " \
    camera-service \
    camx-dlkm \
    camx-hamoa \
    camx-kodiak \
    camx-lemans \
    camx-nhx \
    camx-talos \
    gst-plugins-imsdk-prop \
    iris-video-dlkm \
    kgsl-dlkm \
    libdiag-bin \
    onnxruntime-qnn \
    qcom-adreno \
    qcom-sensors-binaries \
    qwes \
"
CORE_IMAGE_BASE_INSTALL:append = " \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-audioreach', ' packagegroup-audioreach', '', d)} \
"

RTSS_MAILBOX_PACKAGES = "qcom-rtss-mailbox-dlkm qcom-rtss-mailbox-umd qcom-rtss-mailbox-umd-utils"
CORE_IMAGE_BASE_INSTALL:append:qcs9100-ride-sx = " ${RTSS_MAILBOX_PACKAGES}"
CORE_IMAGE_BASE_INSTALL:append:qcs8300-ride-sx = " ${RTSS_MAILBOX_PACKAGES}"
CORE_IMAGE_BASE_INSTALL:append:iq-9075-evk = " ${RTSS_MAILBOX_PACKAGES}"
CORE_IMAGE_BASE_INSTALL:append:iq-8275-evk = " ${RTSS_MAILBOX_PACKAGES}"

TOOLCHAIN_HOST_TASK:append = " nativesdk-protobuf-camx-compiler"
