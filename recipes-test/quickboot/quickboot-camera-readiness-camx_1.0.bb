SUMMARY = "QuickBoot CAMX camera first-frame readiness test"
DESCRIPTION = "Measures display-independent CAMX camera readiness by capturing \
one frame through GStreamer without requiring a display server."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = " \
    file://quickboot-camera-readiness-camx \
    file://quickboot-camera-readiness-camx.service \
"

S = "${UNPACKDIR}"

inherit systemd

# This application uses the Qualcomm CAMX camera stack and is supported on
# ARMv8 platforms only. Keep it out of the non-ARM layer-check machines.
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

RDEPENDS:${PN} += " \
    camera-service \
    gstreamer1.0 \
    gst-plugins-imsdk-prop-qticamsrc \
"

SYSTEMD_SERVICE:${PN} = "quickboot-camera-readiness-camx.service"
# Ship the test application in the image, but leave it opt-in at runtime.
SYSTEMD_AUTO_ENABLE = "disable"

do_install() {
    install -D -m 0755 ${S}/quickboot-camera-readiness-camx \
        ${D}${bindir}/quickboot-camera-readiness-camx
    install -D -m 0644 ${S}/quickboot-camera-readiness-camx.service \
        ${D}${systemd_system_unitdir}/quickboot-camera-readiness-camx.service
}
