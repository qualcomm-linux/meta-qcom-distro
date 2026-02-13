DISTRO_NAME = "Qualcomm Linux Reference Distro"

DISTRO_VERSION = "2.0"

# SDK variables.
SDK_VERSION = "${DISTRO_VERSION}"
SDK_NAME = "${DISTRO}-${SDKMACHINE}-${IMAGE_BASENAME}-${TUNE_PKGARCH}-${MACHINE}"
SDK_VENDOR = "-qcomsdk"

TARGET_VENDOR = "-qcom"

# Ensure 'qcom-distro' appear at most once in DISTROOVERRIDES
DISTROOVERRIDES =. "${@ 'qcom-distro:' if d.getVar('DISTRO') != 'qcom-distro' else ''}"

DISTRO_FEATURES:append = " \
    bluetooth \
    efi \
    glvnd \
    opencl \
    opengl \
    overlayfs \
    pam \
    pni-names \
    ptest \
    security \
    tpm2 \
    virtualization \
    vulkan \
    wayland \
    wifi \
    x11 \
"

# Use systemd init manager for system initialization.
INIT_MANAGER = "systemd"

# Activate systemd watchdog
WATCHDOG_RUNTIME_SEC:pn-systemd = "30"

# Use rsyslog as default syslog utility
VIRTUAL-RUNTIME_base-utils-syslog = "rsyslog"

# Prepare a flat image directory structure suitable to flash with QDL
IMAGE_CLASSES += "image_types_qcom"
IMAGE_FSTYPES += "qcomflash"

# Set a default package class to avoid surprises
PACKAGE_CLASSES = "package_rpm"

# Avoid to duplicate the rootfs tarball by generating both tar.gz/tar.xz
IMAGE_FSTYPES:remove = "tar.gz"

# Pull in the initrd image by default
INITRAMFS_IMAGE = "initramfs-rootfs-image"

INHERIT += "buildhistory"
INHERIT += "image-buildinfo"
INHERIT += "recipe_sanity"

BUILDHISTORY_COMMIT = "1"

require conf/distro/include/security_flags.inc
require conf/distro/include/yocto-space-optimize.inc
require conf/distro/include/yocto-uninative.inc

INHERIT += "uninative"

# Disable weston idle timeout
PACKAGECONFIG:append:pn-weston-init = " no-idle-timeout"
