SUMMARY = "Qualcomm test packagegroup"
DESCRIPTION = "Package group to bring in packages required to test images"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS:${PN} = "\
    igt-gpu-tools-tests \
    iperf3 \
    iproute2 \
    libkcapi \
    lmbench \
    media-ctl \
    opencv-apps \
    pulseaudio-misc \
    v4l-utils \
    yavta \
    "

RDEPENDS:${PN}:append:aarch64 = " \
    fastrpc-tests \
    "
