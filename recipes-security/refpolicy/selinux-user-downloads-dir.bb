SUMMARY = "Create Downloads directories for users"
DESCRIPTION = "Creates Downloads directories for root and future users on SELinux-enabled images."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch

ALLOW_EMPTY:${PN} = "1"

SELINUX_ENABLED = "${@bb.utils.contains('DISTRO_FEATURES', 'selinux', '1', '0', d)}"

DOWNLOAD_DIRS = " \
    ${sysconfdir}/skel/Downloads \
    ${ROOT_HOME}/Downloads \
"

do_install() {
    if [ "${SELINUX_ENABLED}" = "1" ]; then
        for dir in ${DOWNLOAD_DIRS}; do
            install -d ${D}${dir}
        done
    fi
}

FILES:${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'selinux', ' ${DOWNLOAD_DIRS}', '', d)}"
