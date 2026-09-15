SUMMARY = "Early Audio Boot Optimizations"
DESCRIPTION = "Installs kernel module load list for faster audio \
 driver probes during boot"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PACKAGE_ARCH = "${MACHINE_ARCH}"

AUDIOREACH_MODULE = "${@bb.utils.contains('BBFILE_COLLECTIONS', \
        'meta-audioreach', 'file://audioreach-modules.conf', '', d)}"

# audio-modules-common.conf lists all generic modules
# audio-modules.conf is machine-specific and available at "files/${MACHINE}/"

SRC_URI = "file://audio-modules-common.conf \
           file://audio-modules.conf \
           ${AUDIOREACH_MODULE}"

S = "${UNPACKDIR}"

do_install() {
    install -m 0644 -D -t ${D}${sysconfdir}/modules-load.d/ \
        ${UNPACKDIR}/audio-modules.conf ${UNPACKDIR}/audio-modules-common.conf

    if [ -n "${AUDIOREACH_MODULE}" ]; then
        install -m 0644 ${UNPACKDIR}/audioreach-modules.conf \
            ${D}${sysconfdir}/modules-load.d/quickboot-audioreach.conf
    fi
}

FILES:${PN} = "${sysconfdir}/modules-load.d/"
