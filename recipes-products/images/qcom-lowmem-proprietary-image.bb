require qcom-lowmem-image.bb

SUMMARY = "Low-memory image with proprietary camera support"
DESCRIPTION = "A low-memory-footprint image based on qcom-lowmem-image, \
extended with proprietary Qualcomm camera features and components for \
memory-constrained targets."

CORE_IMAGE_BASE_INSTALL:append = " \
    packagegroup-qcom-camera-prop \
    "
