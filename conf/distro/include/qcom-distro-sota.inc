require conf/distro/sota.conf.inc

INITRAMFS_IMAGE = "initramfs-ostree-image"

DISTRO_NAME:append = " (OTA-enabled)"

# ptest is not generally useful with ostree-based images (ro /usr)
DISTRO_FEATURES_BACKFILL_CONSIDERED += "ptest"

# Generate an OSTree repository tarball during image build
BUILD_OSTREE_REPO_TARBALL = "1"
