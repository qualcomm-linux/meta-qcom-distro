SUMMARY = "Qualcomm security packagegroup"
DESCRIPTION = "Package group to bring in packages required for security"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS:${PN}:append:aarch64 = " \
    minkipc-qteesupplicant \
    "
