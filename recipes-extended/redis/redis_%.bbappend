FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " file://0006-tests-modules-do-not-force-host-gcc.patch"

SYSTEMD_AUTO_ENABLE:${PN}:qcom-distro = "disable"

# xxhash's always_inline NEON helpers fail to inline under -Og (from
# DEBUG_BUILD = "1"), which GCC treats as a hard error since
# XXH_NO_INLINE_HINTS only disables forced inlining for -Os/-Oz, not -Og.
CFLAGS:append:qcom-distro = " -DXXH_NO_INLINE_HINTS=1"
