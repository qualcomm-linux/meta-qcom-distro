FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-meson.build-fix-openat2-include-typo-fix-with-glibc-.patch \
    file://0001-meson.build-fix-open_how-include-with-glibc-2.43.patch \
"
