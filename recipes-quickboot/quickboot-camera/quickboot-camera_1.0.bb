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
    install -D -m 0644 ${UNPACKDIR}/camera-modules-common.conf \
        ${D}${sysconfdir}/modules-load.d/quickboot-camera.conf
}

# Generic machine-specific install: uses ${MACHINE} so no per-machine block is needed.
# To add a new machine: add SRC_URI:append:<machine> + files/<machine>/camera-modules.conf.
do_install:append() {
    if [ -f ${UNPACKDIR}/camera-modules.conf ]; then
        install -D -m 0644 ${UNPACKDIR}/camera-modules.conf \
            ${D}${sysconfdir}/modules-load.d/quickboot-camera-${MACHINE}.conf
    fi
}

FILES:${PN} = " \
    ${sysconfdir}/modules-load.d/quickboot-camera*.conf \
"

# iq-9075-evk specific:
# - camcc_sa8775p: BSP kernel module (kernel-module-camcc-sa8775p)
# - camera_qcs9100: out-of-tree DLKM from camx-dlkm_1.0.5.bb
RDEPENDS:${PN}:append:iq-9075-evk = " \
    kernel-module-camcc-sa8775p \
    camx-dlkm \
"
