SUMMARY = "Content-addressed OCI image store and composefs mount tool"
HOMEPAGE = "https://github.com/foundriesio/ocicfs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=3e50796147bee776fba45230e96f618b"
require ${BPN}-licenses.inc

GO_IMPORT = "github.com/foundriesio/ocicfs"
GO_INSTALL = "${GO_IMPORT}/cmd/ocicfs"

SRC_URI = "git://${GO_IMPORT}.git;protocol=https;branch=main;tag=v${PV};destsuffix=${GO_SRCURI_DESTSUFFIX}"
SRCREV = "46a73d4597ace6d22c07a43433d915c76082294b"
require ${BPN}-go-mods.inc

inherit go-mod go-mod-update-modules

# Match upstream's static build; go.bbclass's -linkshared requires cgo.
export CGO_ENABLED = "0"
GO_LINKSHARED = ""
# 32-bit ARM PIE needs cgo; upstream builds/tests only these hosts.
COMPATIBLE_HOST = "(aarch64|x86_64).*-linux"
GO_EXTRA_LDFLAGS = "-X ${GO_IMPORT}/pkg/version.Version=v${PV} -X ${GO_IMPORT}/pkg/version.Revision=${SRCREV}"

# Build-time fs-verity policy: set once in local.conf or the distro for
# ocicfs and cfs-snapshotter's shared store:
#   required (default): require store filesystem support (enabled in ext4 rootfs).
#   auto: use if supported; otherwise skip content checks, reported only by
#         "ocicfs version".
#   off: disable fs-verity.
OCICFS_VERITY ??= "required"
GOBUILDFLAGS:append = " ${@{'required': '-tags=ocicfs_verity_required', 'auto': '', 'off': '-tags=ocicfs_verity_off'}[d.getVar('OCICFS_VERITY')]}"

# Exclude sources: their packaging scripts add make/bash to the -dev package.
do_install() {
    install -Dm 0755 ${B}/${GO_BUILD_BINDIR}/ocicfs ${D}${bindir}/ocicfs
}

# Pulls need mkcomposefs for EROFS and CA certificates for registry HTTPS.
RDEPENDS:${PN} = "composefs ca-certificates"
