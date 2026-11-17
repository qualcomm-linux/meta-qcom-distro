#!/bin/sh
# initramfs-framework module, installed as /init.d/80-bootdisk so it runs
# after 01-udev and before 90-rootfs.
#
# Restrict the root= lookup to the disk holding the EFI System Partition the
# boot loader was started from. systemd-boot and systemd-stub export that
# partition's GPT UUID as LoaderDevicePartUUID. When the variable is absent,
# or no matching partition exists on that disk, root= is left untouched.
#
# The sysfs files are read with shell builtins: forking a tool per block
# device costs several hundred milliseconds on disks with many partitions.

BOOTDISK_EFIVAR="LoaderDevicePartUUID-4a67b082-0a4c-41cf-b6c7-440b29bb8c4f"

bootdisk_enabled() {
	[ "${bootparam_bootdisk:-1}" != "0" ] || return 1
	case "${bootparam_root:-}" in
		PARTLABEL=?*|LABEL=?*) ;;
		*) return 1 ;;
	esac
	[ -e "$EFI_DIR/efivars/$BOOTDISK_EFIVAR" ]
}

# Succeed if the file $1 contains the line $2
bootdisk_has_line() {
	while read -r line; do
		[ "$line" = "$2" ] && return 0
	done < "$1"
	return 1
}

# Set bootdisk_disk to the whole-disk sysfs directory holding the partition
# with the (lower case) PARTUUID $1
bootdisk_find_disk() {
	# PARTUUIDs are unique, so udev's link is safe to use when it exists
	if [ -e "/dev/disk/by-partuuid/$1" ]; then
		dev=$(readlink -f "/dev/disk/by-partuuid/$1")
		bootdisk_disk=$(readlink -f "/sys/class/block/${dev##*/}/..")
		return 0
	fi
	# udev may only have been triggered for the root device: the kernel
	# exports PARTUUID in the partition uevent since v6.13
	for part in /sys/class/block/*/partition; do
		part=${part%/partition}
		if bootdisk_has_line "$part/uevent" "PARTUUID=$1"; then
			bootdisk_disk=$(readlink -f "$part/..")
			return 0
		fi
	done
	return 1
}

# Set bootdisk_dev to the partition on $bootdisk_disk matching root=
bootdisk_find_root() {
	case "$bootparam_root" in
		PARTLABEL=*)
			for part in "$bootdisk_disk"/*/partition; do
				part=${part%/partition}
				if bootdisk_has_line "$part/uevent" "PARTNAME=${bootparam_root#PARTLABEL=}"; then
					bootdisk_dev=/dev/${part##*/}
					return 0
				fi
			done
			;;
		LABEL=*)
			devs=
			dbs=
			for part in "$bootdisk_disk"/*/partition; do
				part=${part%/partition}
				read -r majmin < "$part/dev"
				devs="$devs /dev/${part##*/}"
				dbs="$dbs /run/udev/data/b$majmin"
			done
			# udev already probed the filesystems when it was triggered
			# for every device, so look in its database first
			set -- $(grep -lxF "E:ID_FS_LABEL=${bootparam_root#LABEL=}" $dbs 2>/dev/null)
			if [ -n "$1" ]; then
				part=$(readlink -f "/sys/dev/block/${1#/run/udev/data/b}")
				bootdisk_dev=/dev/${part##*/}
				return 0
			fi
			# busybox and util-linux blkid both print LABEL="..." per device
			blkid_out=$(blkid $devs 2>/dev/null)
			while read -r dev tags; do
				case " $tags" in
					*" LABEL=\"${bootparam_root#LABEL=}\""*)
						bootdisk_dev=${dev%:}
						return 0
						;;
				esac
			done <<-EOF
				$blkid_out
			EOF
			;;
	esac
	return 1
}

bootdisk_run() {
	# The variable is 4 bytes of attributes followed by a UTF-16LE string;
	# the kernel prints PARTUUID in lower case
	esp=$(tail -c +5 "$EFI_DIR/efivars/$BOOTDISK_EFIVAR" | tr -d '\000' | tr 'A-F' 'a-f')
	[ -n "$esp" ] || return

	# Removable media can show up late, so wait as long as rootfs would
	C=0
	delay=${bootparam_rootdelay:-1}
	timeout=${bootparam_roottimeout:-5}
	while ! bootdisk_find_disk "$esp"; do
		if [ $(( C * delay )) -gt $timeout ]; then
			msg "bootdisk: ESP $esp not found, keeping root=$bootparam_root"
			return
		fi
		sleep $delay
		C=$(( C + 1 ))
	done

	if bootdisk_find_root; then
		info "bootdisk: using $bootdisk_dev for root=$bootparam_root (ESP $esp)"
		bootparam_root=$bootdisk_dev
	else
		msg "bootdisk: no root=$bootparam_root on ${bootdisk_disk##*/}, keeping it unchanged"
	fi
}
