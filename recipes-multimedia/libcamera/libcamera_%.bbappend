PACKAGECONFIG:append:qcom-distro = " gst"

FILESEXTRAPATHS:prepend:qcom := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom = " \
    file://0001-libcamera-dma_buf_allocator-Make-provider-priority-c.patch \
"
