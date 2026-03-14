FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://0006-libunwind-fix-strchr-conformance-to-ISO-C23.patch \
"
