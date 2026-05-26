FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-selinux-allow-ModemManager-to-send-DBus-messages-to-.patch \
    file://0002-selinux-allow-seatd-to-use-unallocated-TTYs.patch \
"
