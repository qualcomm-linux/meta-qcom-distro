require qcom-minimal-image.bb

SUMMARY = "Basic console image"

IMAGE_FEATURES += "${@bb.utils.contains('DISTRO_FEATURES', 'sota', '', 'package-management', d)} \
                   ssh-server-openssh"

CORE_IMAGE_BASE_INSTALL += " \
    packagegroup-qcom-utilities-debug-utils \
    packagegroup-qcom-utilities-network-utils \
    packagegroup-qcom-utilities-support-utils \
    packagegroup-qcom-virtualization \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    libgomp \
"
