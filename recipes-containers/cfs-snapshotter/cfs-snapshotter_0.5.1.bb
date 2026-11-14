SUMMARY = "composefs-backed remote snapshotter for containerd"
HOMEPAGE = "https://github.com/foundriesio/cfs-snapshotter"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=3e50796147bee776fba45230e96f618b"
require ${BPN}-licenses.inc

GO_IMPORT = "github.com/foundriesio/cfs-snapshotter"
GO_INSTALL = "${GO_IMPORT}/cmd/containerd-cfs-grpc"

SRC_URI = "git://${GO_IMPORT}.git;protocol=https;branch=main;tag=v${PV};destsuffix=${GO_SRCURI_DESTSUFFIX} \
           file://containerd-config.toml \
           file://docker-cfs-snapshotter.conf \
          "
SRCREV = "8762ca91808c9b2ad7d10e14fbfc3b774c11df22"
require ${BPN}-go-mods.inc

inherit go-mod go-mod-update-modules systemd

# Match upstream's static build; go.bbclass's -linkshared requires cgo.
export CGO_ENABLED = "0"
GO_LINKSHARED = ""
# 32-bit ARM PIE needs cgo; upstream builds/tests only these hosts.
COMPATIBLE_HOST = "(aarch64|x86_64).*-linux"
GO_EXTRA_LDFLAGS = "-X ${GO_IMPORT}/pkg/version.Version=v${PV} -X ${GO_IMPORT}/pkg/version.Revision=${SRCREV}"

# Set OCICFS_VERITY for both recipes: they share a store.
OCICFS_VERITY ??= "required"
GOBUILDFLAGS:append = " ${@{'required': '-tags=ocicfs_verity_required', 'auto': '', 'off': '-tags=ocicfs_verity_off'}[d.getVar('OCICFS_VERITY')]}"

PACKAGECONFIG ??= ""
# docker-default: select cfs via a docker.service drop-in; leave daemon.json
# to the user. Images pulled with another driver no longer show up. Otherwise
# keep Docker's default; select cfs with ctr --snapshotter cfs or daemon.json.
PACKAGECONFIG[docker-default] = ""
# preloaded: serve only images stored by ocicfs.
PACKAGECONFIG[preloaded] = ",,,ocicfs"

do_install() {
    install -Dm 0755 ${B}/${GO_BUILD_BINDIR}/containerd-cfs-grpc ${D}${bindir}/containerd-cfs-grpc

    install -Dm 0644 ${S}/src/${GO_IMPORT}/debian/cfs-snapshotter.service \
        ${D}${systemd_system_unitdir}/cfs-snapshotter.service
    install -Dm 0644 ${S}/src/${GO_IMPORT}/debian/cfs-snapshotter.default \
        ${D}${sysconfdir}/default/cfs-snapshotter
    if ${@bb.utils.contains('PACKAGECONFIG', 'preloaded', 'true', 'false', d)}; then
        sed -i 's|^CFS_OPTS=.*|CFS_OPTS="-admit=preloaded"|' \
            ${D}${sysconfdir}/default/cfs-snapshotter
    fi

    # config.toml must import conf.d; neither QLI nor the containerd recipe ships it.
    install -Dm 0644 ${S}/src/${GO_IMPORT}/docs/containerd-cfs.toml \
        ${D}${sysconfdir}/containerd/conf.d/containerd-cfs.toml
    install -Dm 0644 ${UNPACKDIR}/containerd-config.toml ${D}${sysconfdir}/containerd/config.toml
    if ${@bb.utils.contains('PACKAGECONFIG', 'docker-default', 'true', 'false', d)}; then
        install -Dm 0644 ${UNPACKDIR}/docker-cfs-snapshotter.conf \
            ${D}${systemd_system_unitdir}/docker.service.d/cfs-snapshotter.conf
    fi
}

SYSTEMD_SERVICE:${PN} = "cfs-snapshotter.service"
FILES:${PN} += "${systemd_system_unitdir}/docker.service.d"
CONFFILES:${PN} = " \
    ${sysconfdir}/default/cfs-snapshotter \
    ${sysconfdir}/containerd/config.toml \
    ${sysconfdir}/containerd/conf.d/containerd-cfs.toml \
"

# containerd loads the snapshotter; pulls need mkcomposefs.
RDEPENDS:${PN} = "virtual-containerd composefs"
# vxn owns /etc/containerd/config.toml.
RCONFLICTS:${PN} = "vxn"
