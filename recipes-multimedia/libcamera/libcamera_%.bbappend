PACKAGECONFIG:append:qcom-distro = " gst"

FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-libcamera-dma_buf_allocator-Make-provider-priority-c.patch \
"
