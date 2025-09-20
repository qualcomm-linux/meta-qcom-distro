SUMMARY = "Extended proprietary packages for QCOM platforms"

# Incompatible with all archs except aarch64 (ARMv8)
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    iris-video-dlkm \
    kgsl-dlkm \
"
