FILESEXTRAPATHS:prepend:qcom := "${THISDIR}/${BPN}:"

SRC_URI:append:qcom = " \
	file://defconfig.qcom \
	file://0002-UPSTREAM-WNM-Extend-workaround-for-broken-AP-operating-class-behavior.patch \
"

do_configure:prepend:qcom() {
	install -m 0644 ${UNPACKDIR}/defconfig.qcom ${B}/wpa_supplicant/defconfig
}
