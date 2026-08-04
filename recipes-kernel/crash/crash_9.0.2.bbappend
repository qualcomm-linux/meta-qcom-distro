FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
	file://0001-Makefile-support-cross-compiling-for-32-bit-arm.patch \
"
