require qcom-console-image.bb

SUMMARY = "Image with Networking features"

CORE_IMAGE_BASE_INSTALL += " \
    sigma-dut \
"
