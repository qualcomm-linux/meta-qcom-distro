SUMMARY = "Early camera boot optimizations"
DESCRIPTION = "Installs kernel module preload lists for accelerated camera bring-up."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PACKAGE_ARCH = "${MACHINE_ARCH}"

# camera-modules-common.conf lists all generic modules.
# camera-modules.conf is machine-specific and available at "files/${MACHINE}/".
SRC_URI = " \
    file://camera-modules-common.conf \
    file://camera-modules.conf \
"

S = "${UNPACKDIR}"

do_install() {
    install -m 0644 -D -t ${D}${sysconfdir}/modules-load.d/ \
        ${UNPACKDIR}/camera-modules.conf ${UNPACKDIR}/camera-modules-common.conf
}

FILES:${PN} = "${sysconfdir}/modules-load.d/"
