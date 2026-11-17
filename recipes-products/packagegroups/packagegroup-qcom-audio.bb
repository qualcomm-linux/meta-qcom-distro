SUMMARY = "Qualcomm audio subsystem packagegroup"
DESCRIPTION = "Packagegroup containing audio stack components and utilities \
for audio subsystem bring-up on Qualcomm platforms"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

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
