require conf/distro/sota.conf.inc

INITRAMFS_IMAGE = "initramfs-ostree-image"

DISTRO_NAME:append = " (OTA-enabled)"

# Generate an OSTree repository tarball during image build
BUILD_OSTREE_REPO_TARBALL = "1"
