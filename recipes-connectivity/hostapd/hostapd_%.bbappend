# use latest version from the lts-connectivity-mixin layer
DEFAULT_PREFERENCE:layer-lts-connectivity-mixin:qcom-distro = "1"

FILESEXTRAPATHS:prepend:qcom := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom = " \
	file://defconfig.qcom \
"

do_configure:prepend:qcom() {
	install -m 0644 ${UNPACKDIR}/defconfig.qcom ${UNPACKDIR}/defconfig
}
