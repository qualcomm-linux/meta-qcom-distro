FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://0001-quickboot-start-pipewire-from-alsa-device.patch \
    file://01-quickboot-pipewire.rules \
    file://quickboot-pipewire-manager.socket.conf \
    file://quickboot-pipewire.socket.conf \
"

# The device event starts PipeWire early. Normal systemd activation remains
# available as a fallback.

do_install:append() {
    install -D -m 0644 ${UNPACKDIR}/01-quickboot-pipewire.rules \
        ${D}${nonarch_base_libdir}/udev/rules.d/01-quickboot-pipewire.rules
    install -D -m 0644 ${UNPACKDIR}/quickboot-pipewire-manager.socket.conf \
        ${D}${systemd_system_unitdir}/pipewire-manager.socket.d/quickboot.conf
    install -D -m 0644 ${UNPACKDIR}/quickboot-pipewire.socket.conf \
        ${D}${systemd_system_unitdir}/pipewire.socket.d/quickboot.conf
}

FILES:${PN}:append = " \
    ${nonarch_base_libdir}/udev/rules.d/01-quickboot-pipewire.rules \
    ${systemd_unitdir}/system/pipewire-manager.socket.d/quickboot.conf \
    ${systemd_unitdir}/system/pipewire.socket.d/quickboot.conf \
"
