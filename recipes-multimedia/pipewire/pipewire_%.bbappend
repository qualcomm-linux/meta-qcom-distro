# Enable pipewire-pulse as a system-wide service
SYSTEMD_SERVICE:${PN}-pulse = "pipewire-pulse.service"
SYSTEMD_AUTO_ENABLE:${PN}-pulse = "enable"
SYSTEMD_PACKAGES += "${PN}-pulse"

USERADD_PARAM:${PN} = "--system --home / --no-create-home \
                       --comment 'PipeWire multimedia daemon' \
                       --gid pipewire --groups audio,video,kmem \
                       pipewire"

FILES:${PN}-pulse += "${systemd_unitdir}/system-preset/98-pipewire-pulse.preset"
