require qcom-minimal-image.bb

SUMMARY = "Basic console image"

LICENSE = "BSD-3-Clause-Clear"

IMAGE_FEATURES += "package-management ssh-server-openssh"

CORE_IMAGE_BASE_INSTALL += " \
    packagegroup-qcom-utilities-support-utils \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    libgomp \
"
