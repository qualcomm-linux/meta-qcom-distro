python __anonymous() {
    if "qcom-distro" in d.getVar("DISTROOVERRIDES").split(":"):
        d.setVarFlag("PACKAGECONFIG", "suiteb", ",,")
}

do_configure:append:qcom-distro() {
    if ${@ bb.utils.contains('PACKAGECONFIG', 'suiteb', 'true', 'false', d) }; then
        echo 'CONFIG_SUITEB=y'   >> wpa_supplicant/.config
        echo 'CONFIG_SUITEB192=y' >> wpa_supplicant/.config
    fi
}
