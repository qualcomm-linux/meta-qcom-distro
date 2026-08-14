FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " file://0006-tests-modules-do-not-force-host-gcc.patch"

SYSTEMD_AUTO_ENABLE:${PN}:qcom-distro = "disable"
