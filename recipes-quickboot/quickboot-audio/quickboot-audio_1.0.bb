SUMMARY = "Early Audio Boot Optimizations"
DESCRIPTION = "Installs kernel module load list for faster audio \
driver probes during boot"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Install common modules first, then machine-specific ones.
SRC_URI = "file://audio-modules-common.conf"
SRC_URI:append:iq-9075-evk = " file://audio-modules.conf"

# audioreach_driver is an out-of-tree kernel module provided by meta-audioreach layer.
# Install it only when meta-audioreach layer is present.
AUDIOREACH_MODULE = "${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-audioreach', 'audioreach_driver', '', d)}"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d/
    install -m 0644 ${UNPACKDIR}/audio-modules-common.conf \
        ${D}${sysconfdir}/modules-load.d/quickboot-audio.conf

    # Append AudioReach out-of-tree module when meta-audioreach layer is present
    if [ -n "${AUDIOREACH_MODULE}" ]; then
        echo "${AUDIOREACH_MODULE}" >> \
            ${D}${sysconfdir}/modules-load.d/quickboot-audio.conf
    fi
}

do_install:append:iq-9075-evk() {
    cat ${UNPACKDIR}/audio-modules.conf \
        >> ${D}${sysconfdir}/modules-load.d/quickboot-audio.conf
}

FILES:${PN} = " \
    ${sysconfdir}/modules-load.d/quickboot-audio.conf \
"

RDEPENDS:${PN} = "udev"
