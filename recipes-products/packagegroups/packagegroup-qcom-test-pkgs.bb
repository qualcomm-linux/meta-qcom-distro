SUMMARY = "Qualcomm test packagegroup"
DESCRIPTION = "Package group to bring in packages required to test images"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS:${PN} = "\
    fastrpc-tests \
    igt-gpu-tools-tests \
    iperf3 \
    iproute2 \
    libkcapi \
    lmbench \
    media-ctl \
    pulseaudio-misc \
    v4l-utils \
    yavta \
    "
