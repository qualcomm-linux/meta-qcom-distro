# Mount the root filesystem from the disk the system was booted from
PACKAGE_INSTALL:append:qcom-distro = " initramfs-module-bootdisk"
