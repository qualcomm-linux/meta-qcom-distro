# Go binaries on ARMv7 are built in a strange way
INSANE_SKIP:${PN}:append:arm:qcom-distro = " textrel"

# workaround for permissions preventing rm_work to succeed
do_rm_work:prepend:qcom-distro() {
    if [ -d ${UNPACKDIR} ] ; then
        chmod u+w ${UNPACKDIR} -R
    fi
}

