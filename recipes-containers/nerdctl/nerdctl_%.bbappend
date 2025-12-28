INSANE_SKIP:${PN}:arm:qcom-distro += "textrel"


# workaround for permissions preventing rm_work to succeed
do_rm_work:prepend:qcom-distro() {
    chmod u+w ${UNPACKDIR} -R
}
