SUMMARY = "Qualcomm security packagegroup"
DESCRIPTION = "Package group to bring in packages required for security"

inherit packagegroup

PACKAGES = "${PN}"

RDEPENDS:${PN} = " \
    ${@bb.utils.contains('COMBINED_FEATURES', 'tpm2', 'packagegroup-security-tpm2 tpm2-tools', '', d)} \
    "

# The minkipc-qteesupplicant recipe is compatible only with aarch64 (ARMv8).
RDEPENDS:${PN}:append:aarch64 = " \
    minkipc-qteesupplicant \
    "
