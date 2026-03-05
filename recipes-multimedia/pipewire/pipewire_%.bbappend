# Enable pipewire-pulse as a system-wide service
SYSTEMD_SERVICE:${PN}-pulse:qcom-distro = "pipewire-pulse.service"
SYSTEMD_AUTO_ENABLE:${PN}-pulse:qcom-distro = "enable"
SYSTEMD_PACKAGES:append:qcom-distro = " ${PN}-pulse"

FILES:${PN}-pulse:append:qcom-distro = " ${systemd_unitdir}/system-preset/98-pipewire-pulse.preset"

# Enable aptx
EXTRA_OEMESON:remove = " -Dbluez5-codec-aptx=disabled "
PACKAGECONFIG[bluez-aptx]     = "-Dbluez5-codec-aptx=enabled,-Dbluez5-codec-aptx=disabled,libfreeaptx"
PACKAGECONFIG:append:class-target = " bluez-aptx"
