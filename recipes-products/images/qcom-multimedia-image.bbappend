# In bbappend, so it doesn't affect other images which are based on
# qcom-multimedia-image

# Prevent closed-source packages from being installed into the image
BAD_RECOMMENDATIONS += " \
    libfastcvdsp-stub1 \
    libfastcvopt1 \
    libvulkan-adreno1 \
"

# Error out if any of the closed source packages get pulled into the image
INCOMPATIBLE_LICENSE = "LicenseRef-LICENSE.qcom LicenseRef-LICENSE.qcom-2"

# Allow closed source firmware packages
INCOMPATIBLE_LICENSE_EXCEPTIONS = "\
    camxfirmware-hamoa:LicenseRef-LICENSE.qcom-2 \
    camxfirmware-kodiak:LicenseRef-LICENSE.qcom-2 \
    camxfirmware-lemans:LicenseRef-LICENSE.qcom-2 \
    camxfirmware-monaco:LicenseRef-LICENSE.qcom-2 \
    camxfirmware-talos:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-glymur:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-iq-x7181:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-kaanapali:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-qcs615:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-qcs6490:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-qcs8300:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-qcs9100:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-qrb2210:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-qrb2210-rb1:LicenseRef-LICENSE.qcom \
    firmware-qcom-boot-shikra:LicenseRef-LICENSE.qcom-2 \
    firmware-qcom-boot-sm8750:LicenseRef-LICENSE.qcom-2 \
    trusted-firmware-a-qcom-rb3gen2:LicenseRef-LICENSE.qcom \
"

# QA check considers packages in INCOMPATIBLE_LICENSE_EXCEPTIONS list still to
# be an error. Disable the check as we need to include boot firmware into the
# image.
ERROR_QA:remove = "license-exception"
