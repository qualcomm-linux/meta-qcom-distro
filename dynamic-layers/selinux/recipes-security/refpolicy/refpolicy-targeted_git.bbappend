FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:qcom = " \
    file://0001-container-Allow-access-to-etc-cdi-for-CDI-configurat.patch \
"
