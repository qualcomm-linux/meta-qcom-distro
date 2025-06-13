SUMMARY = "Packagegroups for XFCE related tasks"
DESCRIPTION = "Package group bringing XFCE to your device"

inherit packagegroup

PACKAGES = "${PN}"

# Epiphany dlopen()s libgles3, drag in mesa-demos both as a workaround as well
# as having the demos available
RDEPENDS:${PN} = " \
    cheese \
    eog \
    epiphany \
    evince \
    gedit \
    mesa-demos \
    packagegroup-xfce-base \
"
