FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom-distro = " \
    file://0001-net-stmmac-add-PMD-for-Synopsys-DWC-EQoS-GMAC4.patch \
"

PACKAGECONFIG:append:qcom-distro = " shared afxdp"
