SUMMARY = "TensorFlow Lite C API Library"
DESCRIPTION = "TensorFlow Lite C API Library for embedded systems"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

inherit cmake pkgconfig features_check

# TensorFlow Lite version and branch
TF_LITE_VERSION = "${PV}"
TF_LITE_MAJOR = "${@d.getVar('PV').split('.')[0]}"
TF_LITE_MINOR = "${@d.getVar('PV').split('.')[1]}"
TF_LITE_PATCH = "0"
TF_LITE_BRANCH = "r${TF_LITE_MAJOR}.${TF_LITE_MINOR}"

SRCREV_FORMAT = "tensorflow_farmhash_gemmlowp_cpuinfo_mlDtypes_ruy_openclHeaders_vulkanHeaders_xnnpack_fft2d_fp16_kleidiai_pthreadpool_fxdiv"

# Main TensorFlow repository revision
SRCREV_tensorflow = "72fbba3d20f4616d7312b5e2b7f79daf6e82f2fa"

# Third-party repositories with their revisions
# NOTE: Dependencies are managed manually
# To update dependencies:
# 1. For each repo, run: git ls-remote <url> HEAD
# 2. Get the latest commit hash
# 3. Update the corresponding REV variable in this recipe
# 4. Verify builds still work correctly

# IMPORTANT: These dependencies are downloaded via SRC_URI and integrated into TensorFlow Lite build
# They are NOT packaged separately because:
# 1. TensorFlow Lite uses FETCHCONTENT_FULLY_DISCONNECTED=ON in CMake
# 2. The dependencies are fetched from pre-downloaded sources in SRC_URI
# 3. This ensures version consistency with TensorFlow Lite's expectations
# 4. Packaging them separately would break the dependency chain and versioning
# 5. The build system integrates them directly from the downloaded git repositories

SRCREV_farmhash = "0d859a811870d10f53a594927d0d0b97573ad06d"
SRCREV_gemmlowp = "16e8662c34917be0065110bfcd9cc27d30f52fdf"
SRCREV_cpuinfo = "de0ce7c7251372892e53ce9bc891750d2c9a4fd8"
SRCREV_mlDtypes = "00d98cd92ade342fef589c0470379abb27baebe9"
SRCREV_ruy = "3286a34cc8de6149ac6844107dfdffac91531e72"
SRCREV_openclHeaders = "dcd5bede6859d26833cd85f0d6bbcee7382dc9b3"
SRCREV_vulkanHeaders = "32c07c0c5334aea069e518206d75e002ccd85389"
SRCREV_xnnpack = "585e73e63cb35c8a416c83a48ca9ab79f7f7d45e"
SRCREV_fft2d = "fa0ad63f8b666836f56a823de546390a6e4ff4b6"
SRCREV_fp16 = "4dfe081cf6bcd15db339cf2680b9281b8451eeb3"
SRCREV_kleidiai = "dc69e899945c412a8ce39ccafd25139f743c60b1"
SRCREV_pthreadpool = "c2ba5c50bb58d1397b693740cf75fad836a0d1bf"
SRCREV_fxdiv = "63058eff77e11aa15bf531df5dd34395ec3017c8"
SRC_URI[sha256sum] = "cfc7749b96f63bd31c3c42b5c471bf756814053e847c10f3eb003417bc523d30"

REQUIRED_DISTRO_FEATURES += "opengl"

DEPENDS += " \
    protobuf-native \
    protobuf \
    flatbuffers-tflite-native \
    flatbuffers-tflite \
    jpeg \
    libeigen \
    opencl-headers \
    virtual/egl \
"

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
    https://www.apache.org/licenses/LICENSE-2.0.txt \
"

PACKAGECONFIG ?= "gpu"
PACKAGECONFIG[gpu] = " -DTFLITE_ENABLE_GPU=ON, -DTFLITE_ENABLE_GPU=OFF, virtual/libopencl1 vulkan-headers,"

OECMAKE_SOURCEPATH = "${S}/tensorflow/lite/c"
OECMAKE_TARGET_COMPILE += "benchmark_model label_image"

EXTRA_OECMAKE += " \
    -DFETCHCONTENT_FULLY_DISCONNECTED=ON \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DCMAKE_SYSTEM_NAME=Linux \
    -DTFLITE_HOST_TOOLS_DIR=${STAGING_BINDIR_NATIVE} \
    -DCMAKE_FIND_PACKAGE_PREFER_CONFIG=ON \
    -DProtobuf_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
    -DTFLITE_ENABLE_XNNPACK=ON \
    -DTFLITE_ENABLE_NNAPI=OFF \
    -DTFLITE_ENABLE_RUY=ON \
    -DTFLITE_ENABLE_INSTALL=ON \
    -DTFLITE_ENABLE_LABEL_IMAGE=ON \
    -DTFLITE_ENABLE_BENCHMARK_MODEL=ON \
    -DCPUINFO_SUPPORTED_PLATFORM=ON \
    -DCPUINFO_BUILD_UNIT_TESTS=OFF \
    -DTF_MAJOR_VERSION=${TF_LITE_MAJOR} \
    -DTF_MINOR_VERSION=${TF_LITE_MINOR} \
    -DTF_PATCH_VERSION=${TF_LITE_PATCH} \
    -DTF_VERSION_SUFFIX= \
    -DFP16_SOURCE_DIR=${S}/FP16 \
    -DKLEIDIAI_SOURCE_DIR=${S}/kleidiai \
    -DPTHREADPOOL_SOURCE_DIR=${S}/pthreadpool \
    -DFXDIV_SOURCE_DIR=${S}/FXdiv \
"

# Symlink third-party directories
do_configure:prepend() {
    mkdir -p ${WORKDIR}/build
    cd ${WORKDIR}/build
    
    # Create symbolic links for third-party dependencies
    ln -sf ${S}/third_party/farmhash farmhash
    ln -sf ${S}/third_party/gemmlowp gemmlowp
    ln -sf ${S}/third_party/cpuinfo cpuinfo
    ln -sf ${S}/third_party/ml_dtypes ml_dtypes
    ln -sf ${S}/third_party/ruy ruy
    ln -sf ${S}/third_party/opencl_headers opencl_headers
    ln -sf ${S}/third_party/vulkan_headers vulkan_headers
    ln -sf ${S}/third_party/xnnpack xnnpack
    ln -sf ${S}/fft2d/src/fft2d/fft2d fft2d

    mkdir -p opengl_headers
    cp ${WORKDIR}/LICENSE-2.0.txt opengl_headers/opengl_headers_LICENSE.txt

    mkdir -p egl_headers
    cp ${WORKDIR}/LICENSE-2.0.txt egl_headers/egl_headers_LICENSE.txt
}

# Disable ARM BF16 support for clang toolchain builds
EXTRA_OECMAKE:append:toolchain-clang = " -DXNNPACK_ENABLE_ARM_BF16=OFF"

# Header files to install
TFLITE_HEADERS = " \
    tensorflow/lite \
    tensorflow/compiler/mlir/lite \
    tensorflow/core/public \
    tensorflow/core/platform \
    tensorflow/core/lib \
    tensorflow/lite/examples/label_image \
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
