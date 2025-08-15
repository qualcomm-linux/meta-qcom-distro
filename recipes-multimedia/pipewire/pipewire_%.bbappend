# Enable pipewire-pulse as a system-wide service
SYSTEMD_SERVICE:${PN}-pulse = "pipewire-pulse.service"
SYSTEMD_AUTO_ENABLE:${PN}-pulse = "enable"
SYSTEMD_PACKAGES += "${PN}-pulse"

FILES:${PN}-pulse += "${systemd_unitdir}/system-preset/98-pipewire-pulse.preset"
