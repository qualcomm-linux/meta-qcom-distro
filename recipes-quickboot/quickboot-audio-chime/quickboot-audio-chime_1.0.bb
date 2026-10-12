SUMMARY = "QuickBoot audio readiness test application"
DESCRIPTION = "Plays a chime through PipeWire as a time-to-first-sound \
checkpoint for QuickBoot profiling."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = " \
    file://quickboot-audio-chime \
    file://quickboot-audio-chime.service \
    file://sample-3s.wav \
"

S = "${UNPACKDIR}"

inherit systemd

RDEPENDS:${PN} += " \
    pipewire \
    pipewire-tools \
"

SYSTEMD_SERVICE:${PN} = "quickboot-audio-chime.service"
# Ship the test application in the image, but leave it opt-in at runtime.
SYSTEMD_AUTO_ENABLE = "disable"

do_install() {
    install -D -m 0755 ${S}/quickboot-audio-chime \
        ${D}${bindir}/quickboot-audio-chime
    install -D -m 0644 ${S}/quickboot-audio-chime.service \
        ${D}${systemd_system_unitdir}/quickboot-audio-chime.service
    install -D -m 0644 ${S}/sample-3s.wav \
        ${D}${datadir}/sounds/sample-3s.wav
}
