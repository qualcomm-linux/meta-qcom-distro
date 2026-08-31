require qcom-minimal-image.bb

SUMMARY = "Low-memory image with camera and audio support"
DESCRIPTION = "A low-memory-footprint image based on qcom-minimal-image, \
extended with camera (libcamera) and audio (ALSA/PipeWire/GStreamer) \
support for memory-constrained targets."

CORE_IMAGE_BASE_INSTALL:append = " \
    packagegroup-qcom-audio \
    packagegroup-qcom-camera \
    packagegroup-qcom-utilities-network-utils \
    "
