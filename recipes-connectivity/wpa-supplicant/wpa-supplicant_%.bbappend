PV = "2.12"

LIC_FILES_CHKSUM = "file://COPYING;md5=5ebcb90236d1ad640558c3d3cd3035df \
                    file://README;beginline=1;endline=56;md5=155e35cb3d6ab0d6a17524f48f4e761c \
                    file://wpa_supplicant/wpa_supplicant.c;beginline=1;endline=12;md5=f5ccd57ea91e04800edb88267bf8eae4"

SRC_URI:remove = " \
    file://0001-macsec_linux-Hardware-offload-requires-Linux-headers.patch \
    file://0002-defconfig-Update-Opportunistic-Wireless-Encryption-O.patch \
    file://0003-defconfig-Document-IEEE-802.11be-as-a-published-amen.patch \
    file://0004-defconfig-Uncomment-CONFIG_IEEE80211BE-y.patch \
    file://CVE-2025-24912-01.patch \
    file://CVE-2025-24912-02.patch \
"

SRC_URI[sha256sum] = "08e23937e16d0155e55cab2b51f51fbe10d80a1aa91c4e15442645059b737ef6"
