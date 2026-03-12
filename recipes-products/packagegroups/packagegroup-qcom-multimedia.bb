SUMMARY = "Qualcomm multimedia packagegroups"
DESCRIPTION = "Package groups to bring in packages required to enable multimedia support"

LICENSE = "BSD-3-Clause-Clear"

PACKAGE_ARCH = "${TUNE_PKGARCH}"

inherit packagegroup

PROVIDES = "${PACKAGES}"

PACKAGES = "${PN}"

RDEPENDS:${PN} = "\
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-rtsp-server \
    tensorflow-lite \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'packagegroup-qcom-containers', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'packagegroup-qcom-k8s', '', d)} \
    packagegroup-qcom-opencv \
    packagegroup-qcom-camera \
    packagegroup-qcom-display \
    packagegroup-qcom-audio \
    "

RDEPENDS:${PN}:append:qcom-custom-distro = "\
    packagegroup-qcom-fastcv \
    packagegroup-qcom-graphics \
    packagegroup-qcom-iot-base-utils \
    packagegroup-qcom-video \
    packagegroup-qcom-sailmb-usr \
    packagegroup-qcom-sailmb-ipcc \
    "

