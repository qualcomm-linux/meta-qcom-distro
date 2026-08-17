SUMMARY = "Qualcomm benchmark packagegroup"
DESCRIPTION = "Package group to bring in benchmarking packages"

inherit packagegroup

RDEPENDS:${PN} = "\
    coremark \
    coremark-pro \
    dhrystone \
    fio \
    glmark2 \
    iperf2 \
    lmbench \
    mbw \
    memtester \
    netperf \
    osbench \
    phoronix-test-suite \
    ramspeed \
    ramspeed-smp \
    sockperf \
    sysbench \
    unixbench \
    wrk \
    wrk2 \
    "
