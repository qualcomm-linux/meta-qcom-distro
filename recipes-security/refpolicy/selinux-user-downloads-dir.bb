SUMMARY = "Create Downloads directories for users"
DESCRIPTION = "Creates Downloads directories for root and future users on SELinux-enabled images."
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

ALLOW_EMPTY:${PN} = "1"

do_install:append:qcom-distro-selinux() {
    install -d ${D}${sysconfdir}/skel/Downloads
    install -d ${D}/root/Downloads
}

FILES:${PN}:append:qcom-distro-selinux = " \
    ${sysconfdir}/skel/Downloads \
    /root/Downloads \
"
