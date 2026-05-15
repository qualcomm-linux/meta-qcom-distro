SUMMARY = "Qualcomm benchmark packagegroup"
DESCRIPTION = "Package group to bring in benchmarking packages"

inherit packagegroup

RDEPENDS:${PN} = "\
    coremark \
    dhrystone \
    fio \
    glmark2 \
    iperf2 \
    iperf3 \
    lmbench \
    mbw \
    memtester \
    netperf \
    phoronix-test-suite \
    "
