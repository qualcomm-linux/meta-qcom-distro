SUMMARY = "Userspace utilities for QCOM platforms"

inherit packagegroup

PACKAGES = " \
    ${PN}-filesystem-utils \
    ${PN}-network-utils \
    ${PN}-support-utils \
    "

RDEPENDS:${PN}-filesystem-utils = " \
    e2fsprogs \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    e2fsprogs-resize2fs \
    e2fsprogs-tune2fs \
    "

RDEPENDS:${PN}-network-utils = " \
    ethtool \
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
    procps \
    "
