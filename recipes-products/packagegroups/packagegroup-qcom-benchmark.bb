SUMMARY = "Qualcomm benchmark packagegroup"
DESCRIPTION = "Package group to bring in benchmarking packages"

inherit packagegroup

RDEPENDS:${PN} = "\
    coremark \
    glmark2 \
    "
