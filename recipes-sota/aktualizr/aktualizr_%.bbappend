# meta-qcom-distro does not provision an OTA service backend by default
SYSTEMD_AUTO_ENABLE:${PN}:qcom-distro = "disable"
SYSTEMD_AUTO_ENABLE:${PN}-secondary:qcom-distro = "disable"
