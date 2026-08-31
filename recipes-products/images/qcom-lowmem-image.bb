require qcom-minimal-image.bb

SUMMARY = "Low memory image with camera and audio support"
DESCRIPTION = "A low-memory-footprint image based on qcom-minimal-image, \
extended with camera (CamX/libcamera) and audio (ALSA/PipeWire/GStreamer) \
support for memory-constrained targets."

# This image is compatible only with aarch64 (ARMv8)
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

CORE_IMAGE_BASE_INSTALL:append = " \
    packagegroup-qcom-audio \
    packagegroup-qcom-camera \
    "
