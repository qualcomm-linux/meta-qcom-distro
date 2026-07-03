SUMMARY = "Boot time analysis tools from CentOS Automotive SIG"
DESCRIPTION = "Tools for analyzing and visualizing system boot time"
HOMEPAGE = "https://gitlab.com/CentOS/automotive/src/boot-time-analysis-tools"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1c2e0cc0dec0b709fe547806b55737b0"

# Fetch from GitLab over HTTPS
SRC_URI = "git://gitlab.com/CentOS/automotive/src/boot-time-analysis-tools.git;protocol=https;branch=main"

# Pin to a specific commit (get this from GitLab → Repository → Commits)
SRCREV = "2409ce37e7389d8079bdb8740cb6a44b505fb8d4"

# Source directory after fetch
#S = "${WORKDIR}/git"

# If it's Python-based:
inherit setuptools3 systemd

# Or if it's just scripts with no build system:
# inherit allarch

#do_install() {
#        install -d ${D}${bindir}
#        install -m 0755 ${S}/boot-time-analysis ${D}${bindir}/
#        # Add other scripts/tools as needed
#}

# Runtime dependency on Python 3
RDEPENDS:${PN} = "python3 python3-dbus python3-systemd python3-misc"
