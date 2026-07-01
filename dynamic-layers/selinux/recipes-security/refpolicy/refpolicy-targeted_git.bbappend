FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom = " \
    file://0001-container-Allow-access-to-etc-cdi-for-CDI-configurat.patch \
    file://0002-wayland-Add-wayland_stream_connect-interface.patch \
    file://0003-wayland-Label-sockets-under-run-with-wayland_runtime.patch \
    file://0004-docker-Add-tunable-gated-optional-policy-for-dockerd.patch \
    file://0005-selinux-Add-type-and-access-policy-for-Docker-home-d.patch \
"
