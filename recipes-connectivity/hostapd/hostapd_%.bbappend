PV = "2.12"

LIC_FILES_CHKSUM = "file://hostapd/README;beginline=5;endline=47;md5=4d666937756a064d6d90d128a32c3571"

SRC_URI:remove = " \
    file://0001-Include-base64-for-hostapd-CONFIG_SAE_PK-builds.patch \
    file://0002-hostapd-Fix-clearing-up-settings-for-color-switch.patch \
    file://CVE-2025-24912-01.patch \
    file://CVE-2025-24912-02.patch \
"

SRC_URI[sha256sum] = "f43502561c28ba47ab77e18e1a973d07361c68cc8b14178e619bd5796b70eabd"
