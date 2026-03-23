FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-audioreach', \
        ' file://dmaheap.conf', '', d)} \
"

do_install:append:qcom-distro() {
    if ${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-audioreach', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}/pipewire.service.d
        install -m 0644 ${UNPACKDIR}/dmaheap.conf \
            ${D}${systemd_system_unitdir}/pipewire.service.d/dmaheap.conf
    fi
}

# Enable pipewire-pulse as a system-wide service
SYSTEMD_SERVICE:${PN}-pulse:qcom-distro = "pipewire-pulse.service"
SYSTEMD_AUTO_ENABLE:${PN}-pulse:qcom-distro = "enable"
SYSTEMD_PACKAGES:append:qcom-distro = " ${PN}-pulse"

FILES:${PN}-pulse:append:qcom-distro = " ${systemd_unitdir}/system-preset/98-pipewire-pulse.preset"
