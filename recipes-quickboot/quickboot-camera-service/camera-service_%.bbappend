FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " \
    file://0001-quickboot-qti-cam-server-activate-from-cam-req-mgr.patch \
    file://02-quickboot-qti-cam-server.rules \
"

# The service is activated by the cam-req-mgr udev add event, rather than by
# multi-user.target.
SYSTEMD_AUTO_ENABLE:${PN}:qcom-distro = "disable"

do_install:append:qcom-distro() {
    install -D -m 0644 ${UNPACKDIR}/02-quickboot-qti-cam-server.rules \
        ${D}${nonarch_base_libdir}/udev/rules.d/02-quickboot-qti-cam-server.rules
}

FILES:${PN}:append:qcom-distro = " \
    ${nonarch_base_libdir}/udev/rules.d/02-quickboot-qti-cam-server.rules \
"
