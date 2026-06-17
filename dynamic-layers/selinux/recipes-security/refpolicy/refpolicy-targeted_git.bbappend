FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-Enable-the-tunable-flag-tee_supplicant_qtee.patch \
"
