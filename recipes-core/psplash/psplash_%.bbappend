FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SPLASH_IMAGES:qcom-distro = "file://psplash-qcom-img.png;outsuffix=default"

SRC_URI:append:qcom-distro = " file://psplash-start@.service"

PACKAGECONFIG:remove:qcom-distro = "progress-bar"
