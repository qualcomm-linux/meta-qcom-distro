FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom-distro = " \
    file://0002-wayland-Add-wayland_stream_connect-interface.patch \
    file://0003-wayland-Label-sockets-under-run-with-wayland_runtime.patch \
"
