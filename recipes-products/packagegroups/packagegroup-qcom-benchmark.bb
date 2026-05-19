SUMMARY = "Qualcomm benchmark packagegroup"
DESCRIPTION = "Package group to bring in benchmarking packages"

inherit packagegroup

RDEPENDS:${PN} = "\
    coremark \
    glmark2 \
    iperf2 \
    lmbench \
    netperf \
    sysbench \
    "
