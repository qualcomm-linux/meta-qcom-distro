SUMMARY = "Memory Efficient Serialization Library (TFLite Compatible)"
DESCRIPTION = "FlatBuffers is a cross-platform serialization library used for efficient data serialization and deserialization."
# "This recipe provides version 24.3.25 specifically for TensorFlow Lite 2.20.0.qcom compatibility, which requires an exact match of flatbuffers version 24.3.25 due to strict static assertions in its schema files."

HOMEPAGE = "https://github.com/google/flatbuffers"
SECTION = "console/tools"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV = "23.5.26"
SRCREV = "0100f6a5779831fa7a651e4b67ef389a8752bd9b"
SRC_URI = "git://github.com/google/flatbuffers.git;branch=master;protocol=https;tag=v${PV}"

DEPENDS = "flatbuffers-tflite-native"

inherit cmake python3native

EXTRA_OECMAKE += " \
    -DFLATBUFFERS_BUILD_TESTS=OFF \
    -DFLATBUFFERS_BUILD_SHAREDLIB=ON \
"
EXTRA_OECMAKE:append:class-target = " -DFLATBUFFERS_FLATC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/flatc"

RDEPENDS:${PN}-compiler = "${PN}"
RDEPENDS:${PN}-dev += "${PN}-compiler"

PACKAGE_BEFORE_PN = "${PN}-compiler"

rm_flatc_cmaketarget_for_target() {
    rm -f "${SYSROOT_DESTDIR}/${libdir}/cmake/flatbuffers/FlatcTargets.cmake"
}

SYSROOT_PREPROCESS_FUNCS:class-target += "rm_flatc_cmaketarget_for_target"

FILES:${PN}-compiler = "${bindir}"

BBCLASSEXTEND = "native nativesdk"
