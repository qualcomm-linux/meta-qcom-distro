SUMMARY = "QuickBoot CAMSS camera first-frame readiness test"
DESCRIPTION = "Measures display-independent open-source CAMSS camera readiness \
by capturing one frame through libcamera and GStreamer."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = " \
    file://quickboot-camera-readiness-camss \
    file://quickboot-camera-readiness-camss.service \
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
    libcamera-gst \
"

SYSTEMD_SERVICE:${PN} = "quickboot-camera-readiness-camss.service"
# Ship the test application in the image, but leave it opt-in at runtime.
SYSTEMD_AUTO_ENABLE = "disable"

do_install() {
    install -D -m 0755 ${S}/quickboot-camera-readiness-camss \
        ${D}${bindir}/quickboot-camera-readiness-camss
    install -D -m 0644 ${S}/quickboot-camera-readiness-camss.service \
        ${D}${systemd_system_unitdir}/quickboot-camera-readiness-camss.service
}
