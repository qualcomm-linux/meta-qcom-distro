SUMMARY = "Early Camera Boot Optimizations"
DESCRIPTION = "Installs kernel module load list for faster camera \
driver probes during boot"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Install common modules first, then machine-specific ones.
SRC_URI = "file://camera-modules-common.conf"
SRC_URI:append:iq-9075-evk = " file://camera-modules.conf"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d/
    install -m 0644 ${UNPACKDIR}/camera-modules-common.conf \
        ${D}${sysconfdir}/modules-load.d/quickboot-camera.conf
}

do_install:append:iq-9075-evk() {
    cat ${UNPACKDIR}/camera-modules.conf \
        >> ${D}${sysconfdir}/modules-load.d/quickboot-camera.conf
}

FILES:${PN} = " \
    ${sysconfdir}/modules-load.d/quickboot-camera.conf \
"

RDEPENDS:${PN} = "udev"
