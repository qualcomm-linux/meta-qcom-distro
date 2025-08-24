require qcom-console-image.bb

SUMMARY = "Basic Wayland image with Weston"

LICENSE = "BSD-3-Clause-Clear"

# let's make sure we have a good image.
REQUIRED_DISTRO_FEATURES += "wayland"

CORE_IMAGE_BASE_INSTALL += " \
    packagegroup-qcom-multimedia \
"

#Provide log header support on SDK
TOOLCHAIN_TARGET_TASK:append = " syslog-plumber-dev protobuf-c-dev"

#Provide camera header support on SDK
TOOLCHAIN_TARGET_TASK:append:qcom-custom-bsp:qcm6490 = " camxapi-kt-dev"
TOOLCHAIN_TARGET_TASK:append:qcom-custom-bsp:qcs9100 = " camxapi-dev"
TOOLCHAIN_TARGET_TASK:append:qcom-custom-bsp:qcs8300 = " camxapi-dev"
