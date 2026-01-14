SUMMARY = "Qualcomm security packagegroup"
DESCRIPTION = "Package group to bring in packages required for security"

inherit packagegroup

PACKAGES = "${PN}"

# The minkipc-qteesupplicant recipe is compatible only with aarch64 (ARMv8).
RDEPENDS:${PN}:append:aarch64 = " \
    minkipc-qteesupplicant \
    "
