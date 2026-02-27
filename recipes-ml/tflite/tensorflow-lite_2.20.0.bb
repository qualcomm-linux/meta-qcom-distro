SUMMARY = "TensorFlow Lite C API Library"
DESCRIPTION = "TensorFlow Lite C API Library for embedded systems"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

REQUIRED_DISTRO_FEATURES += "opengl"

DEPENDS += " \
    flatbuffers-tflite \
    flatbuffers-tflite-native \
    jpeg \
    libeigen \
    opencl-headers \
    protobuf \
    protobuf-native \
    virtual/egl \
"

PACKAGECONFIG ?= "gpu"
PACKAGECONFIG[gpu] = " -DTFLITE_ENABLE_GPU=ON, -DTFLITE_ENABLE_GPU=OFF, virtual/libopencl1 vulkan-headers,"

# TensorFlow Lite version and branch
TF_LITE_VERSION = "${PV}"
TF_LITE_MAJOR = "${@d.getVar('PV').split('.')[0]}"
TF_LITE_MINOR = "${@d.getVar('PV').split('.')[1]}"
TF_LITE_PATCH = "0"
TF_LITE_BRANCH = "r${TF_LITE_MAJOR}.${TF_LITE_MINOR}"

SRCREV_FORMAT = "tensorflow_cpuinfo_farmhash_fft2d_fp16_fxdiv_gemmlowp_kleidiai_mlDtypes_openclHeaders_pthreadpool_ruy_vulkanHeaders_xnnpack"

# Main TensorFlow repository revision
SRCREV_tensorflow = "72fbba3d20f4616d7312b5e2b7f79daf6e82f2fa"

# Third‑party dependency revisions used by TensorFlow Lite
#
# How to update these SRCREV_* values:
#
# 1) Use the automated script 'extract_tflite_srcrevs_from_github.py' to generate
#    the latest SRCREV values for all dependencies.
#
# 2) Run the script with the target TensorFlow version:
#    python3 extract_tflite_srcrevs_from_github.py v2.20.0
#
# 3) Replace the SRCREV_* values below with the output from the script.
#
# 4) For fft2d specifically, the script automatically fetches from:
#    https://android.googlesource.com/platform/external/fft2d.git
#
# 5) Rebuild and validate.
#
# IMPORTANT: Always use the automated script to ensure SRCREV values match
#            the exact pinned versions in the TensorFlow repository.
# Pinned third‑party revisions (automatically generated)
SRCREV_cpuinfo = "de0ce7c7251372892e53ce9bc891750d2c9a4fd8"
SRCREV_farmhash = "0d859a811870d10f53a594927d0d0b97573ad06d"
SRCREV_fft2d = "fa0ad63f8b666836f56a823de546390a6e4ff4b6"
SRCREV_fp16 = "4dfe081cf6bcd15db339cf2680b9281b8451eeb3"
SRCREV_fxdiv = "63058eff77e11aa15bf531df5dd34395ec3017c8"
SRCREV_gemmlowp = "16e8662c34917be0065110bfcd9cc27d30f52fdf"
SRCREV_kleidiai = "dc69e899945c412a8ce39ccafd25139f743c60b1"
SRCREV_mlDtypes = "00d98cd92ade342fef589c0470379abb27baebe9"
SRCREV_openclHeaders = "dcd5bede6859d26833cd85f0d6bbcee7382dc9b3"
SRCREV_pthreadpool = "c2ba5c50bb58d1397b693740cf75fad836a0d1bf"
SRCREV_ruy = "3286a34cc8de6149ac6844107dfdffac91531e72"
SRCREV_vulkanHeaders = "32c07c0c5334aea069e518206d75e002ccd85389"
SRCREV_xnnpack = "585e73e63cb35c8a416c83a48ca9ab79f7f7d45e"

SRC_URI = " \
    git://github.com/tensorflow/tensorflow.git;name=tensorflow;branch=${TF_LITE_BRANCH};protocol=https;tag=v${TF_LITE_VERSION} \
    file://0001-lite-Add-config-option-to-enable-benchmark_model.patch \
    file://0002-cmake-lite-tools-benchmark-require-protobuf-through-.patch \
    file://0003-feat-tflite-Add-dynamic-OpenCL-library-loading-suppo.patch \
    file://0004-cmake-Exclude-subdirectories-from-all-builds.patch \
    file://0005-c-Force-delegate-symbols-from-shared-library.patch \
    file://0006-c-Add-version-support-to-C-API.patch \
    file://0007-cmake-Fix-label-image-dependencies.patch \
    file://0008-cmake-Add-install-rule-for-c-interface-shared-librar.patch \
    file://0009-tflite-Add-absl-log-dependency-for-enhanced-logging-.patch \
    git://github.com/google/farmhash;name=farmhash;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/farmhash/;branch=master;protocol=https \
    git://github.com/google/gemmlowp.git;name=gemmlowp;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/gemmlowp/;branch=master;protocol=https \
    git://github.com/pytorch/cpuinfo.git;name=cpuinfo;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/cpuinfo/;branch=main;protocol=https \
    git://github.com/jax-ml/ml_dtypes.git;name=mlDtypes;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/ml_dtypes/;branch=main;protocol=https \
    git://github.com/google/ruy.git;name=ruy;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/ruy/;branch=master;protocol=https \
    git://github.com/KhronosGroup/OpenCL-Headers.git;name=openclHeaders;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/opencl_headers/;branch=main;protocol=https \
    git://github.com/KhronosGroup/Vulkan-Headers.git;name=vulkanHeaders;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/vulkan_headers/;branch=main;protocol=https \
    git://github.com/google/XNNPACK.git;name=xnnpack;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/third_party/xnnpack/;branch=master;protocol=https \
    git://android.googlesource.com/platform/external/fft2d.git;name=fft2d;destsuffix=tensorflow-lite-${TF_LITE_VERSION}/fft2d;branch=main;protocol=https \
    git://github.com/Maratyszcza/FP16.git;protocol=https;branch=master;name=fp16;destsuffix=${S}/FP16 \
    git://github.com/ARM-software/kleidiai.git;protocol=https;branch=main;name=kleidiai;destsuffix=${S}/kleidiai \
    git://github.com/google/pthreadpool.git;protocol=https;branch=main;name=pthreadpool;destsuffix=${S}/pthreadpool \
    git://github.com/Maratyszcza/FXdiv.git;protocol=https;branch=master;name=fxdiv;destsuffix=${S}/FXdiv \
    file://0001-Revert-Fix-XNNPACK-build-failure-with-when-mcpu-comp.patch \
    file://0001-Fix-syscall-undeclared-error-if-built-with-stricter-.patch;patchdir=third_party/cpuinfo \
    file://0001-Support-OE-specific-arm-value-for-CMAKE_SYSTEM_PROCE.patch;patchdir=third_party/cpuinfo \
"

inherit cmake features_check pkgconfig

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite/c"
OECMAKE_TARGET_COMPILE += "benchmark_model label_image"

EXTRA_OECMAKE += " \
    -DCMAKE_FIND_PACKAGE_PREFER_CONFIG=ON \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DCMAKE_SYSTEM_NAME=Linux \
    -DCPUINFO_BUILD_UNIT_TESTS=OFF \
    -DCPUINFO_SUPPORTED_PLATFORM=ON \
    -DFP16_SOURCE_DIR=${S}/FP16 \
    -DFXDIV_SOURCE_DIR=${S}/FXdiv \
    -DKLEIDIAI_SOURCE_DIR=${S}/kleidiai \
    -DProtobuf_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
    -DTFLITE_ENABLE_BENCHMARK_MODEL=ON \
    -DTFLITE_ENABLE_INSTALL=ON \
    -DTFLITE_ENABLE_LABEL_IMAGE=ON \
    -DTFLITE_ENABLE_NNAPI=OFF \
    -DTFLITE_ENABLE_RUY=ON \
    -DTFLITE_ENABLE_XNNPACK=ON \
    -DTFLITE_HOST_TOOLS_DIR=${STAGING_BINDIR_NATIVE} \
    -DTF_MAJOR_VERSION=${TF_LITE_MAJOR} \
    -DTF_MINOR_VERSION=${TF_LITE_MINOR} \
    -DTF_PATCH_VERSION=${TF_LITE_PATCH} \
    -DTF_VERSION_SUFFIX= \
    -DPTHREADPOOL_SOURCE_DIR=${S}/pthreadpool \
"

# Disable ARM BF16 support for clang toolchain builds
EXTRA_OECMAKE:append:toolchain-clang = " -DXNNPACK_ENABLE_ARM_BF16=OFF"

# Symlink third-party directories
do_configure:prepend() {
    mkdir -p ${WORKDIR}/build
    cd ${WORKDIR}/build
    
    # Create symbolic links for third-party dependencies
    ln -sf ${S}/third_party/cpuinfo cpuinfo
    ln -sf ${S}/third_party/farmhash farmhash
    ln -sf ${S}/third_party/gemmlowp gemmlowp
    ln -sf ${S}/third_party/ml_dtypes ml_dtypes
    ln -sf ${S}/third_party/opencl_headers opencl_headers
    ln -sf ${S}/third_party/ruy ruy
    ln -sf ${S}/third_party/vulkan_headers vulkan_headers
    ln -sf ${S}/third_party/xnnpack xnnpack
    ln -sf ${S}/fft2d/src/fft2d/fft2d fft2d

    mkdir -p opengl_headers
    cp ${COMMON_LICENSE_DIR}/Apache-2.0 opengl_headers/opengl_headers_LICENSE.txt

    mkdir -p egl_headers
    cp ${COMMON_LICENSE_DIR}/Apache-2.0 egl_headers/egl_headers_LICENSE.txt
}

# Header files to install
TFLITE_HEADERS = " \
    tensorflow/compiler/mlir/lite \
    tensorflow/core/lib \
    tensorflow/core/platform \
    tensorflow/core/public \
    tensorflow/lite \
"

do_install:append() {
    for HPATH in ${TFLITE_HEADERS}; do
        install -d ${D}${includedir}/$(dirname ${HPATH})
        cd ${S}
        find ${HPATH} \( ! -name "*hexagon*" -name "*.h*" \) -type f | while read header; do
            install -D -m 0644 ${S}/$header ${D}${includedir}/$header
        done
    done

    install -d ${D}${bindir}
    install -m 0755 ${B}/tensorflow-lite/examples/label_image/label_image ${D}${bindir}
    install -m 0755 ${B}/tensorflow-lite/tools/benchmark/benchmark_model ${D}${bindir}
}

PACKAGE_BEFORE_PN += "${PN}-tools"

# Keep ${PN}-tools package name fixed (disable Debian auto-renaming)
DEBIAN_NOAUTONAME:${PN}-tools = "1"

FILES:${PN}-tools = "\
    ${bindir}/benchmark_model \
    ${bindir}/label_image \
"
