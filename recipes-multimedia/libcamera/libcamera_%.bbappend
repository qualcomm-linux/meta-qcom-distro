PACKAGECONFIG:append:qcom-distro = " gst"

FILESEXTRAPATHS:prepend:qcom := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom = " \
    file://0001-libcamera-dma_buf_allocator-Prefer-udmabuf-over-dma-.patch \
"
