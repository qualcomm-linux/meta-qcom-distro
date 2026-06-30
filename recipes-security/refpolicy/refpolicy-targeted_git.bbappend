FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-Allow-network-manager-to-use-bpf-for-IPv4-collision-.patch \
"
