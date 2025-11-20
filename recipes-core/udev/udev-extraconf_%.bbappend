# automount storage devices
PACKAGES =+ "${PN}-automount"
FILES:${PN}-automount =+ "${sysconfdir}/udev/rules.d/automount.rules ${sysconfdir}/udev/scripts/mount.sh ${sysconfdir}/udev/mount.ignorelist"
