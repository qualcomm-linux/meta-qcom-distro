# Enable wireplumber as a system-wide service
SYSTEMD_SERVICE:${PN}:qcom-distro = "wireplumber.service"
SYSTEMD_AUTO_ENABLE:${PN}:qcom-distro = "enable"
