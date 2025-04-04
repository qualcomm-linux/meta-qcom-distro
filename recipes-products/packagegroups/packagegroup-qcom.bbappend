
PACKAGES += " \
    ${PN}-filesystem-utils \
    ${PN}-support-utils \
    "

# libinput is not just a library, it also contains udev rules
RDEPENDS:${PN}-support-utils = " \
    libinput \
    libinput-bin \
    networkmanager \
    procps \
    "

RDEPENDS:${PN}-filesystem-utils = " \
    e2fsprogs \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
    e2fsprogs-resize2fs \
    e2fsprogs-tune2fs \
    "
