SUMMARY = "AI Edge LiteRT Python package"
HOMEPAGE = "https://pypi.org/project/ai-edge-litert/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PV = "2.1.5"
PYPI_PACKAGE = "ai_edge_litert"
WHEEL_NAME = "${PYPI_PACKAGE}-${PV}-cp314-cp314-manylinux_2_27_aarch64.whl"

SRC_URI = "https://files.pythonhosted.org/packages/42/43/2b8ef317dca3096372cbf820aa5a3a6c8857dad67f31840a1e17a951e2d3/${WHEEL_NAME};downloadfilename=${WHEEL_NAME}"
SRC_URI[sha256sum] = "41d2e8267f047ed14f14a5ff4e0f677a9c5143dd17a36991513f8cc5ef580a3b"

S = "${UNPACKDIR}"

inherit python3-dir python3targetconfig

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

DEPENDS += "unzip-native"

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    ${STAGING_BINDIR_NATIVE}/unzip -q ${UNPACKDIR}/${WHEEL_NAME} -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/*"

RDEPENDS:${PN} += "python3-core python3-opencv"
INSANE_SKIP:${PN} += "already-stripped"
