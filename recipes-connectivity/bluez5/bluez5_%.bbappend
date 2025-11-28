#Include and install bluetooth tools which are required.

# Include only desired tools that are conditional on READLINE in bluez
INST_TOOLS_READLINE = " \
    tools/bluetooth-player \
    tools/obexctl \
    tools/btmgmt \
"

# Remove desired tools from noinst-tools
NOINST_TOOLS:remove:qcom = " \
    ${@bb.utils.contains('PACKAGECONFIG', 'readline', '${INST_TOOLS_READLINE}', '', d)} \
"

do_install:append:qcom() {
    # Install desired tools that upstream leaves in build area
    for f in ${INST_TOOLS_READLINE} ; do
        install -m 755 ${B}/$f ${D}/${bindir}
    done
}
