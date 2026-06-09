PACKAGES:prepend:qcom-distro = "${PN}-pactl "
FILES:${PN}-pactl:qcom-distro = "${bindir}/pactl"
FILES:${PN}-server:remove:qcom-distro = "${bindir}/pactl"
