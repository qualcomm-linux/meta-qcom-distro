FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-Allow-network-manager-to-use-bpf-for-IPv4-collision-.patch \
    file://0002-wayland-Add-wayland_stream_connect-interface.patch \
    file://0003-wayland-Label-sockets-under-run-with-wayland_runtime.patch \
    file://0004-docker-Add-tunable-gated-optional-policy-for-dockerd.patch \
    file://0005-xdg-Add-interface-for-mounting-on-Downloads-director.patch \
    file://0006-docker-Add-tunable-for-mounting-on-XDG-Downloads-dir.patch \
    file://0007-container-Allow-access-to-etc-cdi-for-CDI-configurat.patch \
"
