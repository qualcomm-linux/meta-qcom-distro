FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " \
    file://0001-wpa_supplicant-Enable-MBO-in-defconfig-by-default.patch \
    file://0001-wpa_supplicant-Enable-WNM-in-defconfig-by-default.patch \
"

PACKAGECONFIG:append:qcom-distro = " suiteb"
