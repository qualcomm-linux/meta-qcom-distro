SUMMARY = "Qualcomm audio stack packagegroup"
DESCRIPTION = "Packagegroup containing audio framework components, services, \
and utilities required for audio subsystem enablement on Qualcomm platforms."

inherit packagegroup

RDEPENDS:${PN} = "\
    alsa-utils-alsatplg \
    alsa-utils-alsaucm \
    alsa-utils-aplay \
    pipewire \
    pipewire-alsa \
    pipewire-modules-meta \
    pipewire-pulse \
    pipewire-spa-tools \
    pipewire-tools \
    pulseaudio-pactl \
    wireplumber \
    "
