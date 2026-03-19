FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " \
    file://0001-spa-plugins-alsa-acp-compat.h-Fix-missed-Wdiscarded-.patch \
    file://0002-spa-plugins-alsa-acp-compat.h-p-is-already-const-do-.patch \
"

# Enable pipewire-pulse as a system-wide service
SYSTEMD_SERVICE:${PN}-pulse:qcom-distro = "pipewire-pulse.service"
SYSTEMD_AUTO_ENABLE:${PN}-pulse:qcom-distro = "enable"
SYSTEMD_PACKAGES:append:qcom-distro = " ${PN}-pulse"

FILES:${PN}-pulse:append:qcom-distro = " ${systemd_unitdir}/system-preset/98-pipewire-pulse.preset"
