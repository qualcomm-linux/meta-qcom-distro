SUMMARY = "Packagegroups for collecting useful runtime utilities"
DESCRIPTION = "Packagegroup bringing tools that make developing and debuging on-target a more enjoyable experience"

inherit packagegroup

PACKAGES = "packagegroup-qcom-console-utils"

RDEPENDS:packagegroup-qcom-console-utils = " \
    avahi-utils \
    coreutils \
    util-linux \
    git \
    htop \
    vim \
    wget \
"
