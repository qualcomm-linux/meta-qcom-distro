FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://0001-quickboot-start-weston-from-drm-device.patch \
    file://03-quickboot-weston.rules \
    file://quickboot-weston.socket.conf \
"

# The device event starts Weston early. Normal systemd activation remains
# available as a fallback.

do_install:append() {
    install -D -m 0644 ${UNPACKDIR}/03-quickboot-weston.rules \
        ${D}${nonarch_base_libdir}/udev/rules.d/03-quickboot-weston.rules
    install -D -m 0644 ${UNPACKDIR}/quickboot-weston.socket.conf \
        ${D}${systemd_system_unitdir}/weston.socket.d/quickboot.conf
}

FILES:${PN}:append = " \
    ${nonarch_base_libdir}/udev/rules.d/03-quickboot-weston.rules \
    ${systemd_unitdir}/system/weston.socket.d/quickboot.conf \
"
