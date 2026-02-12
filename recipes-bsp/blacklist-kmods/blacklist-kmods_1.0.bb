SUMMARY = "Systemd modprobe.d configuration to blacklist modules"
DISCRIPTION = "Systemd modprobe.d configuration files to blacklist upstream drivers that are enabled by \
               default in qcom-multimedia-image."
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause-Clear;md5=7a434440b651f4a472ca93716d01033a"

SRC_URI += "\
    file://blacklist-audio.conf \
    file://blacklist-video.conf \
"

inherit allarch features_check systemd
REQUIRED_DISTRO_FEATURES = "systemd"

S = "${UNPACKDIR}"

do_compile[noexec] = "1"

do_install() {
    install -Dm 0644 ${UNPACKDIR}/blacklist-audio.conf \
            ${D}${sysconfdir}/modprobe.d/balcklist-audio.conf
    install -Dm 0644 ${UNPACKDIR}/blacklist-video.conf \
            ${D}${sysconfdir}/modprobe.d/balcklist-video.conf
}

PACKAGES = "${PN}"
