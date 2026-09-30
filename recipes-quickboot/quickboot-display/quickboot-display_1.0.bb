SUMMARY = "Early display boot optimizations"
DESCRIPTION = "Installs kernel module preload lists for accelerated display bring-up."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PACKAGE_ARCH = "${MACHINE_ARCH}"

# display-modules-common.conf lists all generic modules
# display-modules.conf is machine-specific and available at "files/${MACHINE}/"

SRC_URI = " \
    file://display-modules-common.conf \
    file://display-modules.conf \
"

S = "${UNPACKDIR}"

do_install() {
    install -m 0644 -D -t ${D}${sysconfdir}/modules-load.d/ \
        ${UNPACKDIR}/display-modules.conf ${UNPACKDIR}/display-modules-common.conf
}

FILES:${PN} = "${sysconfdir}/modules-load.d/"
