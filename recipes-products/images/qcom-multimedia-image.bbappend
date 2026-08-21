# In bbappend, so it doesn't affect other images which are based on
# qcom-multimedia-image

# Prevent closed-source packages from being installed into the image
BAD_RECOMMENDATIONS += " \
    libfastcvdsp-stub1 \
    libfastcvopt1 \
    libvulkan-adreno1 \
    qairt-sdk-hexagon-v66 \
    qairt-sdk-hexagon-v68 \
    qairt-sdk-hexagon-v73 \
    qairt-sdk-hexagon-v75 \
"

# Error out if any of the closed source packages get pulled into the image
INCOMPATIBLE_LICENSE = " \
    LICENSE.qcom \
    LICENSE.qcom-2 \
    qcom-ai-stack \
"

# Allow closed source firmware packages
INCOMPATIBLE_LICENSE_EXCEPTIONS = "\
    camxfirmware-hamoa:LICENSE.qcom-2 \
    camxfirmware-kodiak:LICENSE.qcom-2 \
    camxfirmware-lemans:LICENSE.qcom-2 \
    camxfirmware-monaco:LICENSE.qcom-2 \
    camxfirmware-talos:LICENSE.qcom-2 \
    firmware-qcom-boot-glymur:LICENSE.qcom-2 \
    firmware-qcom-boot-iq-x7181:LICENSE.qcom-2 \
    firmware-qcom-boot-kaanapali:LICENSE.qcom-2 \
    firmware-qcom-boot-qcs615:LICENSE.qcom-2 \
    firmware-qcom-boot-qcs6490:LICENSE.qcom-2 \
    firmware-qcom-boot-qcs8300:LICENSE.qcom-2 \
    firmware-qcom-boot-qcs9100:LICENSE.qcom-2 \
    firmware-qcom-boot-qrb2210:LICENSE.qcom-2 \
    firmware-qcom-boot-qrb2210-rb1:LICENSE.qcom \
    firmware-qcom-boot-shikra:LICENSE.qcom-2 \
    firmware-qcom-boot-sm8750:LICENSE.qcom-2 \
    trusted-firmware-a-qcom:LICENSE.qcom \
    trusted-firmware-a-qcom-rb3gen2:LICENSE.qcom \
    hexagon-dsp-binaries-config-schema \
    hexagon-dsp-binaries-arduino-monza-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-arduino-monza-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-arduino-monza-gdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-db820c-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-qcom-glymur-crd-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-glymur-crd-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-hamoa-iot-evk-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-hamoa-iot-evk-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-iq8275-evk-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-iq8275-evk-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-iq8275-evk-gdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-iq9075-evk-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-iq9075-evk-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-iq9075-evk-gdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-kaanapali-mtp-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-kaanapali-mtp-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-purwa-iot-evk-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-purwa-iot-evk-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-qcm6490-idp-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-qcom-qcm6490-idp-cdsp:LICENSE.qcom \
    hexagon-dsp-binaries-qcom-qcs615-ride-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-qcs615-ride-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-qcs8300-ride-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-qcs8300-ride-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-qcs8300-ride-gdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-sa8775p-ride-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-sa8775p-ride-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-sa8775p-ride-gdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-sdm845-hdk-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-qcom-sdm845-hdk-cdsp:LICENSE.qcom \
    hexagon-dsp-binaries-qcom-shikra-cqm-evk-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-shikra-cqs-evk-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-shikra-iqs-evk-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-sm8750-mtp-adsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-qcom-sm8750-mtp-cdsp:LICENSE.qcom-2 \
    hexagon-dsp-binaries-radxa-dragon-q6a-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-radxa-dragon-q6a-cdsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-db845c-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-db845c-cdsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-db845c-sdsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rb1-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rb2-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rb2-cdsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rb3gen2-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rb3gen2-cdsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rb5-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rb5-cdsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rb5-sdsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rubikpi3-adsp:LICENSE.qcom \
    hexagon-dsp-binaries-thundercomm-rubikpi3-cdsp:LICENSE.qcom \
"

# QA check considers packages in INCOMPATIBLE_LICENSE_EXCEPTIONS list still to
# be an error. Disable the check as we need to include boot firmware into the
# image.
ERROR_QA:remove = "license-exception"
