SUMMARY = "Qualcomm audio chime demo"
DESCRIPTION = "Plays a chime through PipeWire to verify audio readiness and \
measure time to first sound."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = " \
    file://audio-chime \
    file://audio-chime.service \
    file://sample-3s.wav \
"

S = "${UNPACKDIR}"

inherit systemd

RDEPENDS:${PN} += " \
    pipewire \
    pipewire-tools \
"

SYSTEMD_SERVICE:${PN} = "audio-chime.service"
# Ship the demo in the image, but leave it opt-in at runtime.
SYSTEMD_AUTO_ENABLE = "disable"

do_install() {
    install -D -m 0755 ${S}/audio-chime \
        ${D}${bindir}/audio-chime
    install -D -m 0644 ${S}/sample-3s.wav \
        ${D}${datadir}/sounds/sample-3s.wav
    install -D -m 0644 ${S}/audio-chime.service \
        ${D}${systemd_system_unitdir}/audio-chime.service
}
