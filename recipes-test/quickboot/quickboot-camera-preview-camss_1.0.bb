SUMMARY = "QuickBoot CAMSS camera-to-framebuffer preview test"
DESCRIPTION = "Validates open-source CAMSS capture, GStreamer conversion, and \
Linux framebuffer output through an opt-in preview workload and systemd service."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = " \
    file://quickboot-camera-preview-camss \
    file://quickboot-camera-preview-camss.service \
"

S = "${UNPACKDIR}"

inherit systemd

# This application targets camera-enabled ARMv8 platforms. Keep it out of the
# non-ARM layer-check machines.
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

RDEPENDS:${PN} += " \
    coreutils \
    gstreamer1.0 \
    gstreamer1.0-plugins-bad-fbdevsink \
    gstreamer1.0-plugins-base-videoconvertscale \
    libcamera \
    libcamera-gst \
"

SYSTEMD_SERVICE:${PN} = "quickboot-camera-preview-camss.service"
# Ship the test application in the image, but leave it opt-in at runtime.
SYSTEMD_AUTO_ENABLE = "disable"

do_install() {
    install -D -m 0755 ${S}/quickboot-camera-preview-camss \
        ${D}${bindir}/quickboot-camera-preview-camss
    install -D -m 0644 ${S}/quickboot-camera-preview-camss.service \
        ${D}${systemd_system_unitdir}/quickboot-camera-preview-camss.service
}
