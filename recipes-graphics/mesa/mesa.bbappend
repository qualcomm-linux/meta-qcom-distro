FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " file://gallivm-Fix-armhf-build-against-LLVM-22.patch"
