DISTRO_FEATURES:append = " selinux"
DISTRO_FEATURES_FILTER_NATIVE:append = " selinux"
DISTRO_FEATURES_FILTER_NATIVESDK:append = " selinux"

DISTRO_EXTRA_RDEPENDS:append = " packagegroup-core-selinux"

DEFAULT_ENFORCING ?= "permissive"

IMAGE_CLASSES += "selinux-image"

DISTRO_NAME:append = " (SELinux-enabled)"
