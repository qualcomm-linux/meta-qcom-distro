SUMMARY = "initramfs-framework module restricting root= to the disk the EFI boot loader was started from"
DESCRIPTION = "Looks up a PARTLABEL= or LABEL= root= only on the disk holding \
the EFI System Partition exported by systemd-boot or systemd-stub as \
LoaderDevicePartUUID, so another disk with a matching partition can't be \
mounted as root instead. root= is left unchanged when the variable is \
absent or no partition on that disk matches."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
RDEPENDS:${PN} = "initramfs-framework-base ${VIRTUAL-RUNTIME_base-utils}"

SRC_URI = "file://bootdisk.sh"

S = "${UNPACKDIR}"

inherit allarch

do_install() {
    install -d ${D}/init.d
    install -m 0755 ${S}/bootdisk.sh ${D}/init.d/80-bootdisk
}

FILES:${PN} = "/init.d/"
