SUMMARY = "Userspace utilities for QCOM platforms"

inherit packagegroup

PACKAGES += " \
    ${PN}-debug-utils \
    ${PN}-filesystem-utils \
    ${PN}-gpu-utils \
    ${PN}-network-utils \
    ${PN}-support-utils \
    "

# Have ${PN} drag in all of the subpackages
RDEPENDS:${PN} = " \
    ${PN}-debug-utils \
    ${PN}-filesystem-utils \
    ${PN}-gpu-utils \
    ${PN}-network-utils \
    ${PN}-support-utils \
    "

RDEPENDS:${PN}-debug-utils = " \
    gdb \
    strace \
    valgrind \
    "

RDEPENDS:${PN}-filesystem-utils = " \
    e2fsprogs \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    e2fsprogs-resize2fs \
    e2fsprogs-tune2fs \
    "

RDEPENDS:${PN}-gpu-utils = " \
    clinfo \
    kmscube \
    mesa-demos \
    vulkan-tools \
"
RRECOMMENDS:${PN}-gpu-utils = " \
    libopencl-mesa \
"

RDEPENDS:${PN}-network-utils = " \
    ethtool \
    hostapd \
    iproute2 \
    iw \
    networkmanager \
    openssh-scp \
    openssh-ssh \
    rsync \
    smbclient \
    tcpdump \
    "

RDEPENDS:${PN}-support-utils = " \
    dtc \
    efivar \
    file \
    less \
    ltrace \
    procps \
    tinyalsa \
    trace-cmd \
    usbutils \
    "
