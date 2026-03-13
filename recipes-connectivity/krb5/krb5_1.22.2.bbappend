FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-Fix-strchr-conformance-to-C23.patch;striplevel=2 \
"
