FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://default-profile.conf \
            file://98-qcom-pipewire.preset \
           "
do_install:append () {
    install -d ${D}${nonarch_libdir}/systemd/system-preset
    install -m 0644 ${UNPACKDIR}/98-qcom-pipewire.preset ${D}${nonarch_libdir}/systemd/system-preset/98-qcom-pipewire.preset
    install -d ${D}/usr/share/wireplumber/wireplumber.conf.d
    install -m 0644 ${UNPACKDIR}/default-profile.conf ${D}/usr/share/wireplumber/wireplumber.conf.d
}

FILES:${PN} += "${nonarch_libdir}/systemd/system-preset/98-qcom-pipewire.preset"
FILES:${PN} += "/usr/share/wireplumber/wireplumber.conf.d/default-profile.conf"
