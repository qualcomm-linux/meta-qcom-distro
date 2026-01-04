# Go binaries on ARMv7 are built in a strange way
INSANE_SKIP:${PN}:append:arm:qcom-distro = " textrel"
INSANE_SKIP:${PN}:append:arm:qcom-distro-sota = " textrel"

# workaround for permissions preventing rm_work to succeed
do_rm_work:prepend:qcom-distro() {
    if [ -d ${UNPACKDIR} ] ; then
        chmod u+w ${UNPACKDIR} -R
    fi
}

do_rm_work:prepend:qcom-distro-sota() {
    if [ -d ${UNPACKDIR} ] ; then
        chmod u+w ${UNPACKDIR} -R
    fi
}
