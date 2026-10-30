SUMMARY = "QuickBoot audio readiness test application"
DESCRIPTION = "Plays an audio sample through PipeWire as a reproducible \
audio playback readiness workload for QuickBoot validation."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = " \
    file://quickboot-audio-readiness \
    file://quickboot-audio-readiness.service \
"

S = "${UNPACKDIR}"

inherit systemd

RDEPENDS:${PN} += " \
    pipewire \
    pipewire-tools \
    alsa-utils-speaker-test \
"

SYSTEMD_SERVICE:${PN} = "quickboot-audio-readiness.service"
# Ship the test application in the image, but leave it opt-in at runtime.
SYSTEMD_AUTO_ENABLE = "disable"

do_install() {
    install -D -m 0755 ${S}/quickboot-audio-readiness \
        ${D}${bindir}/quickboot-audio-readiness
    install -D -m 0644 ${S}/quickboot-audio-readiness.service \
        ${D}${systemd_system_unitdir}/quickboot-audio-readiness.service
}
