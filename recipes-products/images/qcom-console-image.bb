require qcom-minimal-image.bb

SUMMARY = "Basic console image"

IMAGE_FEATURES += "package-management ssh-server-openssh"

CORE_IMAGE_BASE_INSTALL += " \
    packagegroup-qcom-utilities-debug-utils \
    packagegroup-qcom-utilities-network-utils \
    packagegroup-qcom-utilities-support-utils \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    libgomp \
"
