SUMMARY = "Minimal image"

LICENSE = "BSD-3-Clause-Clear"

IMAGE_FEATURES += "splash tools-debug allow-root-login post-install-logging enable-adbd"

inherit core-image features_check extrausers image-adbd

# let's make sure we have a good image..
REQUIRED_DISTRO_FEATURES = "pam systemd"

CORE_IMAGE_BASE_INSTALL += " \
    kernel-modules \
    packagegroup-qcom-utilities-bluetooth-utils \
    packagegroup-qcom-utilities-filesystem-utils \
    resize-rootfs \
"

# Default root password: oelinux123
EXTRA_USERS_PARAMS = "usermod -p '\$6\$UDMimfYF\$akpHo9mLD4z0vQyKzYxYbsdYxnpUD7B7rHskq1E3zXK8ygxzq719wMxI78i0TIIE0NB1jUToeeFzWXVpBBjR8.' root;"

# Adding kernel-devsrc to provide kernel development support on SDK
TOOLCHAIN_TARGET_TASK += "kernel-devsrc"

# Add RT tools only if the RT kernel is selected
CORE_IMAGE_EXTRA_INSTALL += "${@bb.utils.contains_any('PREFERRED_PROVIDER_virtual/kernel', \
                            'linux-qcom-rt linux-qcom-next-rt', 'rt-tests', '', d)}"
