SUMMARY = "Qualcomm camera preview demo"
DESCRIPTION = "Installs a camera preview demo and an opt-in systemd service."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = " \
    file://camera-preview \
    file://camera-preview.service \
"

S = "${UNPACKDIR}"

inherit systemd features_check

REQUIRED_DISTRO_FEATURES = "wayland"

RDEPENDS:${PN} += " \
    camera-service \
    gstreamer1.0 \
    gst-plugins-imsdk-prop \
    weston \
"

SYSTEMD_SERVICE:${PN} = "camera-preview.service"
# Ship the demo in the image, but leave it opt-in at runtime.
SYSTEMD_AUTO_ENABLE = "disable"

do_install() {
    install -D -m 0755 ${S}/camera-preview \
        ${D}${bindir}/camera-preview
    install -D -m 0644 ${S}/camera-preview.service \
        ${D}${systemd_system_unitdir}/camera-preview.service
}
