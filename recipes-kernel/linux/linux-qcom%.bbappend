FILESEXTRAPATHS:prepend := "${THISDIR}/linux-qcom:"

SRC_URI:append:qcom-distro = " file://configs/distro-additions.cfg"
