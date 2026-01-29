inherit cmake pkgconfig

SUMMARY = "TensorFlow Lite C++ Library"
DESCRIPTION = "TensorFlow Lite C++ Library for embedded systems"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

DEPENDS = " \
    protobuf-native \
    protobuf \
    flatbuffers-native \
    flatbuffers \
    util-linux-native \
    patchelf-native \
    jpeg \
    libeigen \
    opencl-headers \
"
TFLITE_BUILT = "1"

SRCREV = "72fbba3d20f4616d7312b5e2b7f79daf6e82f2fa"
BRANCH = "r${@'.'.join(d.getVar('PV').split('.')[0:2])}"

SRC_URI = "git://github.com/tensorflow/tensorflow.git;branch=${BRANCH};protocol=https \
           file://0001-lite-Add-config-option-to-enable-benchmark_model.patch     \
           file://0002-cmake-lite-tools-benchmark-require-protobuf-through-.patch \
           file://0003-feat-tflite-Add-dynamic-OpenCL-library-loading-suppo.patch \
           file://0004-cmake-Exclude-subdirectories-from-all-builds.patch         \
           file://0005-c-Force-delegate-symbols-from-shared-library.patch         \
           file://0006-c-Add-version-support-to-C-API.patch                       \
           file://0007-cmake-Fix-label-image-dependencies.patch                   \
           file://0008-cmake-Add-install-rule-for-c-interface-shared-librar.patch \
          "

PATCHTOOL = "git"

PACKAGECONFIG ?= "gpu"
# OpenCL support
PACKAGECONFIG[gpu] = " -DTFLITE_ENABLE_GPU=ON, -DTFLITE_ENABLE_GPU=OFF, virtual/libopencl1 vulkan-headers,"

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite/c"

# This should probably be under PACKAGECONFIG control
OECMAKE_TARGET_COMPILE += "\
    benchmark_model \
    label_image \
    "

EXTRA_OECMAKE += "\
    -DFETCHCONTENT_FULLY_DISCONNECTED=OFF \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DCMAKE_SYSTEM_NAME=Linux \
    -DTFLITE_HOST_TOOLS_DIR=${STAGING_BINDIR_NATIVE} \
    -DCMAKE_FIND_PACKAGE_PREFER_CONFIG=ON \
    -DProtobuf_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
    -DTFLITE_ENABLE_XNNPACK=ON \
    -DTFLITE_ENABLE_NNAPI=OFF \
    -DTFLITE_ENABLE_RUY=ON \
    ${@bb.utils.contains('PACKAGECONFIG', 'gpu', '-DTFLITE_ENABLE_GPU=ON', '-DTFLITE_ENABLE_GPU=OFF', d)} \
    -DTFLITE_ENABLE_INSTALL=ON \
    -DTFLITE_ENABLE_LABEL_IMAGE=ON \
    -DTFLITE_ENABLE_BENCHMARK_MODEL=ON \
    -DCMAKE_SYSTEM_PROCESSOR=arm64 \
    -DCPUINFO_SUPPORTED_PLATFORM=ON \
    -DCPUINFO_BUILD_UNIT_TESTS=OFF \
"

COMPILER = "${@bb.utils.contains('TUNE_FEATURES', 'clang', 'clang', 'gcc', d)}"
python () {
    if d.getVar('COMPILER') == 'clang':
        d.appendVar('EXTRA_OECMAKE', ' -DXNNPACK_ENABLE_ARM_BF16=OFF')
}

# Tensorflow lacks a proper install method, so manually specify which headers we want
TFLITE_HEADERS = "tensorflow/lite tensorflow/core/public tensorflow/core/platform tensorflow/core/lib tensorflow/lite/examples/label_image"

do_install:append() {
    for HPATH in ${TFLITE_HEADERS};
    do
        install -d ${D}${includedir}/${HPATH}
        cd ${S}/${HPATH}
        cp --parents $(find . \( ! -name "*hexagon*" -name "*.h*" \)) ${D}${includedir}/${HPATH}
    done

    install -d ${D}${bindir}
    install -m 0755 ${B}/tensorflow-lite/examples/label_image/label_image ${D}${bindir}
    install -m 0755 ${B}/tensorflow-lite/tools/benchmark/benchmark_model ${D}${bindir}

}

FILES:${PN} += " \
    ${libdir}/libtensorflowlite_c.so.* \
    ${bindir}/label_image \
    ${bindir}/benchmark_model \
"
FILES:${PN}-dev += " \
    ${libdir}/libtensorflowlite_c.so \
    ${includedir} \
"

COMPATIBLE_HOST:arm = "null"
COMPATIBLE_HOST:x86 = "null"
