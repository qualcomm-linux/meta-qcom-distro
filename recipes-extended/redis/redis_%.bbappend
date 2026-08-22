FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " file://0006-tests-modules-do-not-force-host-gcc.patch"

SYSTEMD_AUTO_ENABLE:${PN}:qcom-distro = "disable"

# The vendored deps/xxhash always-inline NEON accelerators fail to inline
# under -Og (DEBUG_BUILD), aborting the build before libxxhash.a exists.
CFLAGS:append = " ${@oe.utils.vartrue('DEBUG_BUILD', ' -DXXH_NO_INLINE_HINTS=1', '', d)}"

# The recipe's do_compile:prepend only builds a subset of deps/, omitting
# xxhash and tre even though src/Makefile links redis-server against both.
# A :prepend here cannot suppress the recipe's own do_compile:prepend --
# bitbake concatenates both, so the recipe's unmodified 5-target -C deps
# call would still run afterwards and its distclean cascade would wipe
# out xxhash/tre again. Override do_compile() outright instead, building
# all deps targets in one invocation before the normal make.
do_compile() {
    oe_runmake -C deps hdr_histogram fpconv hiredis lua linenoise xxhash tre
    oe_runmake
}
