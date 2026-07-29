SUMMARY = "Early Display Boot Optimizations"
DESCRIPTION = "Installs kernel module load list for faster \
display driver probes during boot"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://display-modules-common.conf"
SRC_URI:append:iq-9075-evk = " file://display-modules.conf"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/modules-load.d/
    install -m 0644 ${UNPACKDIR}/display-modules-common.conf \
        ${D}${sysconfdir}/modules-load.d/quickboot-display.conf
}

# Generic machine-specific install: uses ${MACHINE} so no per-machine block is needed.
# To add a new machine: add SRC_URI:append:<machine> + files/<machine>/display-modules.conf.
do_install:append() {
    if [ -f ${UNPACKDIR}/display-modules.conf ]; then
        install -m 0644 ${UNPACKDIR}/display-modules.conf \
            ${D}${sysconfdir}/modules-load.d/quickboot-display-${MACHINE}.conf
    fi
}

FILES:${PN} = " \
    ${sysconfdir}/modules-load.d/quickboot-display*.conf \
"

# iq-9075-evk specific — SA8775P clock controllers + eDP PHY
RDEPENDS:${PN}:append:iq-9075-evk = " \
    kernel-module-gpucc-sa8775p \
    kernel-module-dispcc0-sa8775p \
    kernel-module-phy-qcom-edp \
"
