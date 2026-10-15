FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://0001-quickboot-qti-cam-server-activate-from-cam-req-mgr.patch \
    file://02-quickboot-qti-cam-server.rules \
"

# The device event starts the service early. Normal systemd activation remains
# available as a fallback.

do_install:append() {
    install -D -m 0644 ${UNPACKDIR}/02-quickboot-qti-cam-server.rules \
        ${D}${nonarch_base_libdir}/udev/rules.d/02-quickboot-qti-cam-server.rules
}

FILES:${PN}:append = " \
    ${nonarch_base_libdir}/udev/rules.d/02-quickboot-qti-cam-server.rules \
"
