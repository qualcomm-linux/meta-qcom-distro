# In bbappend, so it doesn't affect other images which are based on
# qcom-multimedia-image

# Prevent closed-source packages from being installed into the image
BAD_RECOMMENDATIONS = " \
    libfastcvdsp-stub1 \
    libfastcvopt1 \
    libvulkan-adreno1 \
"

# Error out if any of the closed source packages get pulled into the image
INCOMPATIBLE_LICENSE = "LICENSE.qcom LICENSE.qcom-2"

# Allow closed source firmware and a few dependent packages
INCOMPATIBLE_LICENSE_EXCEPTIONS = "\
    camxfirmware-kodiak:LICENSE.qcom-2 \
    camxfirmware-lemans:LICENSE.qcom-2 \
    camxfirmware-monaco:LICENSE.qcom-2 \
    camxfirmware-talos:LICENSE.qcom-2 \
    camx-kodiak:LICENSE.qcom-2 \
    camx-lemans:LICENSE.qcom-2 \
    camxlib-kodiak:LICENSE.qcom-2 \
    camxlib-lemans:LICENSE.qcom-2 \
    chicdk-kodiak:LICENSE.qcom-2 \
    chicdk-lemans:LICENSE.qcom-2 \
    diag-router:LICENSE.qcom-2 \
    firmware-qcom-boot-glymur:LICENSE.qcom-2 \
    firmware-qcom-boot-iq-x7181:LICENSE.qcom-2 \
    firmware-qcom-boot-qcs615:LICENSE.qcom-2 \
    firmware-qcom-boot-qcs6490:LICENSE.qcom-2 \
    firmware-qcom-boot-qcs8300:LICENSE.qcom-2 \
    firmware-qcom-boot-qcs9100:LICENSE.qcom-2 \
    firmware-qcom-boot-qrb2210-rb1:LICENSE.qcom \
    firmware-qcom-boot-sm8750:LICENSE.qcom-2 \
    libdiag:LICENSE.qcom-2 \
    qcom-sensors-binaries:LICENSE.qcom-2 \
    trusted-firmware-a-qcom:LICENSE.qcom \
"

# QA check considers packages in INCOMPATIBLE_LICENSE_EXCEPTIONS list still to
# be an error. Disable the check as we need to include boot firmware into the
# image.
ERROR_QA:remove = "license-exception"
