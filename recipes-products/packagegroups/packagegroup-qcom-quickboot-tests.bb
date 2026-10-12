SUMMARY = "Qualcomm QuickBoot validation workload package group"
DESCRIPTION = "Package group containing common QuickBoot validation workloads."

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = " \
    quickboot-audio-readiness \
"
