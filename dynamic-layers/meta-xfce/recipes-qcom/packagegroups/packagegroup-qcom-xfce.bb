SUMMARY = "Packagegroups for XFCE related tasks"
DESCRIPTION = "Package group bringing XFCE to your device"

inherit packagegroup

PACKAGES = "packagegroup-qcom-xfce"

# Epiphany dlopen()s libgles3, drag in mesa-demos both as a workaround as well
# as having the demos available
RDEPENDS:packagegroup-qcom-xfce = " \
    cheese \
    eog \
    epiphany \
    evince \
    gedit \
    mesa-demos \
    packagegroup-xfce-base \
"
