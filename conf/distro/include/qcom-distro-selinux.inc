DISTRO_FEATURES:append = " selinux"
DISTRO_FEATURES_FILTER_NATIVE:append = " selinux"
DISTRO_FEATURES_FILTER_NATIVESDK:append = " selinux"

DISTRO_EXTRA_RDEPENDS:append = " packagegroup-core-selinux"

DEFAULT_ENFORCING ?= "enforcing"

# swtpm's selinux PACKAGECONFIG attempts to build SELinux policy
# modules using the host's /usr/share/selinux/devel/Makefile.
# This is not compatible with Yocto cross builds and causes:
# "Is selinux-policy-devel installed?" during do_configure.
# Temporary workaround until fixed upstream in meta-security.
PACKAGECONFIG:remove:pn-swtpm = "selinux"

IMAGE_CLASSES += "selinux-image"

DISTRO_NAME:append = " (SELinux-enabled)"
