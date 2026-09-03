SUMMARY = "Early Audio Boot Optimizations"
DESCRIPTION = "Installs kernel module load list for faster audio \
driver probes during boot"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# 1 if meta-audioreach layer is active, '' otherwise
AUDIOREACH_MODULE = "${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-audioreach', '1', '', d)}"

SRC_URI = "file://audio-modules.conf"
SRC_URI:append = " ${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-audioreach', \
    'file://audioreach-modules.conf', '', d)}"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d/
    install -m 0644 ${UNPACKDIR}/audio-modules.conf \
        ${D}${sysconfdir}/modules-load.d/quickboot-audio.conf

    # Install audioreach_driver as a separate conf file when meta-audioreach is present
    if [ -n "${AUDIOREACH_MODULE}" ]; then
        install -m 0644 ${UNPACKDIR}/audioreach-modules.conf \
            ${D}${sysconfdir}/modules-load.d/quickboot-audioreach.conf
    fi
}

FILES:${PN} = " \
    ${sysconfdir}/modules-load.d/quickboot-audio*.conf \
"

