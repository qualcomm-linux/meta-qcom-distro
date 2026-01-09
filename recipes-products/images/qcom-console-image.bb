require qcom-minimal-image.bb

SUMMARY = "Basic console image"

IMAGE_FEATURES += "${@bb.utils.contains('DISTRO_FEATURES', 'sota', '', 'package-management', d)} \
                   ssh-server-openssh"

CORE_IMAGE_BASE_INSTALL += " \
    packagegroup-qcom-security \
    packagegroup-qcom-utilities-debug-utils \
    packagegroup-qcom-utilities-network-utils \
    packagegroup-qcom-utilities-profile-utils \
    packagegroup-qcom-utilities-support-utils \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'packagegroup-qcom-virtualization', '', d)} \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    libgomp \
"
