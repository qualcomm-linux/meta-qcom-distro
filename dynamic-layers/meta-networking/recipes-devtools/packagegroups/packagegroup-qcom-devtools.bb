SUMMARY = "Packagegroups for collecting useful runtime utilities"
DESCRIPTION = "Packagegroup bringing tools that make developing and debuging on-target a more enjoyable experience"

inherit packagegroup

PACKAGES = "packagegroup-qcom-console-utils"

RDEPENDS:packagegroup-qcom-console-utils = " \
    avahi-utils \
    bash \
    coreutils \
    git \
    htop \
    openssh-scp \
    openssh-ssh \
    rsync \
    samba-client \
    smbclient \
    strace \
    util-linux \
    vim \
    wget \
"
