PACKAGECONFIG:append:qcom-distro = " gst"

FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-libcamera-software_isp-Add-AllocCmaDmaBuffers-constr.patch \
    file://0002-libcamera-pipeline-simple-Do-not-alloc-CMA-DMA-buffe.patch \
"
