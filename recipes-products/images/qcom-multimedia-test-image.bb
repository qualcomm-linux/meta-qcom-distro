require qcom-multimedia-image.bb

SUMMARY = "Multimedia test image"

CORE_IMAGE_BASE_INSTALL += " \
    packagegroup-qcom-test-pkgs \
"
