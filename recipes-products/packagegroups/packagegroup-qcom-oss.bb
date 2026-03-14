SUMMARY = "Packagegroups for OSS / proprietary components control"
DESCRIPTION = "Package group controlling exclusion of proprietary components"

inherit packagegroup

# Proprietary packages
RCONFLICTS:${PN} = " \
    camx-kodiak \
    camx-lemans \
    camx-monaco \
    camx-nhx \
    camx-talos \
    camxlib-kodiak \
    camxlib-lemans \
    camxlib-monaco \
    camxlib-talos \
    chicdk-kodiak \
    chicdk-lemans \
    chicdk-monaco \
    chicdk-talos \
    diag-router \
    fastcv-apps \
    libdiag \
    qairt-sdk \
    qcom-adreno-cl \
    qcom-fastcv-binaries \
    qcom-adreno-common \
    qcom-adreno-egl \
    qcom-adreno-gles1 \
    qcom-adreno-gles2 \
    qcom-adreno-vulkan \
    qcom-sensors-binaries \
    smart-venc-ctrl-algo \
"

# Open-Source kernel modules, but they are useless without the proprietary part

RCONFLICTS:${PN} += " \
    kernel-module-msm-kgsl \
    kernel-module-camera-qcm6490 \
    kernel-module-camera-qcs615 \
    kernel-module-camera-qcs9100 \
"
