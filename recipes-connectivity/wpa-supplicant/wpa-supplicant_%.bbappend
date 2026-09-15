do_configure:append:qcom-distro() {
    if ${@ bb.utils.contains('PACKAGECONFIG', 'suiteb', 'true', 'false', d) }; then
        echo 'CONFIG_SUITEB=y'   >> wpa_supplicant/.config
        echo 'CONFIG_SUITEB192=y' >> wpa_supplicant/.config
    fi

    if ${@ bb.utils.contains('PACKAGECONFIG', 'wnm', 'true', 'false', d) }; then
        echo 'CONFIG_WNM=y' >> wpa_supplicant/.config
    fi

    if ${@ bb.utils.contains('PACKAGECONFIG', 'mbo', 'true', 'false', d) }; then
        echo 'CONFIG_MBO=y' >> wpa_supplicant/.config
    fi
}
