SUMMARY = "Extended proprietary packages for QCOM platforms"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    iris-video-dlkm \
    kgsl-dlkm \
"
