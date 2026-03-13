FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-c23-fix-initialization-discards-const-qualifier-from.patch \
"
