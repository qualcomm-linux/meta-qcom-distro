DISTRO_NAME = "Qualcomm Linux"

DISTRO_VERSION = "1.6-ver.1.0"

# Extend the version string of the Linux kernel to indicate distro
LINUX_VERSION_EXTENSION = "-qli-${DISTRO_VERSION}"

# Supported selections are: base, custom
# We intend to use same selection as that of BSP by default.
# Specific DISTROs might change this as needed.
DISTROOVERRIDES =. "qcom-${QCOM_SELECTED_BSP}-distro:"

# SDK variables.
SDK_VERSION = "${DISTRO_VERSION}"
SDK_NAME = "${DISTRO}-${SDKMACHINE}-${IMAGE_BASENAME}-${TUNE_PKGARCH}-${MACHINE}"
SDK_VENDOR = "-qcomsdk"

TARGET_VENDOR = "-qcom"

# defaultsetup.inc gets includes after ${DISTRO}.conf, so we need to set it here
# to make the python below work. Local, site and auto.conf will override it.
TCMODE ?= "default"

DISTRO_FEATURES:append = " pam overlayfs acl xattr ptest security virtualization tpm usrmerge sota"

# Use systemd init manager for system initialization.
INIT_MANAGER = "systemd"
VIRTUAL-RUNTIME_init_manager = "systemd"
VIRTUAL-RUNTIME_dev_manager  = "udev"

PACKAGECONFIG:append:pn-systemd = " resolved networkd"

# Use rsyslog as default syslog utility
VIRTUAL-RUNTIME_base-utils-syslog = "rsyslog"

# Avoid to duplicate the rootfs tarball by generating both tar.gz/tar.xz
IMAGE_FSTYPES:remove = "tar.gz"

# Pull in the initrd image by default
INITRAMFS_IMAGE_BUNDLE ?= "1"
INITRAMFS_IMAGE = "${@bb.utils.contains('DISTRO_FEATURES', 'sota', 'initramfs-ostree-image', 'initramfs-qcom-image', d)}"

INHERIT += "buildhistory"
INHERIT += "image-buildinfo"
INHERIT += "recipe_sanity"

## Performance build customizations.
# In PERFORMANCE_BUILD let /var/log be volatile (i.e. not on persistent storage)
# by symlinking /var/log to /var/volatile/log
VOLATILE_LOG_DIR = "${@bb.utils.contains('PERFORMANCE_BUILD', '1', 'yes', 'no', d)}"

SERIAL_CONSOLES ?= "${@oe.utils.conditional('PERFORMANCE_BUILD','1','','115200;ttyMSM0',d)}"
# populate console value to be added to cmdline
def get_console_val(d):
    import re

    console = "ttynull"
    consoles = (d.getVar('SERIAL_CONSOLES') or "").split()
    for entry in consoles:
        baudrate = re.sub(r';.*', '', entry)
        tty = re.sub(r'^[0-9]*;', '', entry)
        tty = re.sub(r';.*', '', tty)
        console = f"{tty},{baudrate}n8"
    return f"console={console}"

BUILDHISTORY_COMMIT = "1"

# docker-ce has been deprecated, so switch to docker-moby as the preferred provider.
PREFERRED_PROVIDER_virtual/docker = "docker-moby"

# Enable uninative for reuse -native sstate across hosts.
require conf/distro/include/yocto-uninative.inc
INHERIT += "uninative"

# GCC Version
GCCVERSION = "13.4%"

# Easily switch between rust versions just by changing RUST_VERSION variable
RUST_VERSION ?= "1.75.0"

PREFERRED_VERSION_cargo ?= "${RUST_VERSION}"
PREFERRED_VERSION_cargo-native ?= "${RUST_VERSION}"
PREFERRED_VERSION_libstd-rs ?= "${RUST_VERSION}"
PREFERRED_VERSION_rust ?= "${RUST_VERSION}"
PREFERRED_VERSION_rust-cross-${TARGET_ARCH} ?= "${RUST_VERSION}"
PREFERRED_VERSION_rust-llvm ?= "${RUST_VERSION}"
PREFERRED_VERSION_rust-llvm-native ?= "${RUST_VERSION}"
PREFERRED_VERSION_rust-native ?= "${RUST_VERSION}"

# Enable security compiler and linker flags.
require qcom-security_flags.inc

# Ostree variables
OSTREE_BOOTLOADER ?= "none"
OSTREE_SPLIT_BOOT = "1"
OSTREE_LOADER_LINK = "0"
BUILD_OTA_TARBALL = "1"
OSTREE_KERNEL = "${EFI_LINUX_IMG}"
OSTREE_KERNEL_ARGS_COMMON ?= "root=LABEL=otaroot rootfstype=ext4"
OSTREE_CONSOLE = ""
OSTREE_CONSOLE:qcom-custom-bsp = "${@get_console_val(d)}"
OSTREE_KERNEL_ARGS ?= " ${OSTREE_KERNEL_ARGS_COMMON} ${OSTREE_CONSOLE} ${KERNEL_CMDLINE_EXTRA} "
OSTREE_KERNEL_ARGS:remove = "root=/dev/disk/by-partlabel/system"
SOTA_CLIENT_PROV ?= ""
OSTREE_DEPLOY_DEVICETREE = "0"
OSTREE_DEVICETREE = "${KERNEL_DEVICETREE}"
OSTREE_MULTI_DEVICETREE_SUPPORT = "0"

# Set OPKGLIBDIR to /etc to support OSTree for OTA
OPKGLIBDIR ?= "${@bb.utils.contains('DISTRO_FEATURES', 'sota', '/etc', '/var/lib', d)}"

# Set PREFERRED_PROVIDER virtual/mesa and virtual/libgl as mesa-gl for qcom-custom-bsp when x11 is enabled
PREFERRED_PROVIDER_virtual/mesa:qcom-custom-bsp = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'mesa-gl', 'mesa', d)}"
PREFERRED_PROVIDER_virtual/libgl:qcom-custom-bsp = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'mesa-gl', 'mesa', d)}"
