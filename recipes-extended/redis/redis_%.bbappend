FILESEXTRAPATHS:prepend:qcom-distro := "${THISDIR}/files:"

SRC_URI:append:qcom-distro = " file://0006-tests-modules-do-not-force-host-gcc.patch"

SYSTEMD_AUTO_ENABLE:${PN}:qcom-distro = "disable"

# xxhash.h's XXH_FORCE_INLINE wraps XXH3_accumulate_{neon,scalar} (and other
# internal helpers) in __attribute__((always_inline)). At -Og (DEBUG_BUILD=1)
# GCC's inliner heuristics can't satisfy always_inline for these non-trivial
# functions and hard-errors instead of just losing the optimization -- this
# hits the scalar path exactly the same as the NEON path, so forcing one SIMD
# path over another (e.g. XXH_VECTOR=0) doesn't help. xxhash.h documents
# XXH_NO_INLINE_HINTS for exactly this case: it drops XXH_FORCE_INLINE down to
# a plain `static` (no always_inline attribute) and lets GCC decide.
CFLAGS:append:qcom-distro = " -DXXH_NO_INLINE_HINTS=1"

# redis's do_compile() issues two separate `oe_runmake -C deps ...` calls in
# one generated shell function: this prepend's "tre xxhash", then upstream's
# unmodified "hdr_histogram fpconv hiredis lua linenoise". deps/Makefile has
# two INDEPENDENT ifneq guards (CFLAGS vs cached deps/.make-cflags, LDFLAGS vs
# deps/.make-ldflags), each gating a `distclean` prerequisite -- and
# distclean's recipe unconditionally ends with a blanket `rm -f .make-*` that
# deletes BOTH marker files even when only ONE guard actually mismatched.
# Our -DXXH_NO_INLINE_HINTS=1 CFLAGS addition above makes the CFLAGS guard
# mismatch on the FIRST oe_runmake call, so its distclean fires and (via the
# blanket rm) also deletes .make-ldflags, even though LDFLAGS never changed.
# Call 1 then rebuilds+re-caches .make-cflags and successfully archives
# libtre.a/libxxhash.a -- but moments later call 2 finds .make-ldflags
# missing, treats that as an LDFLAGS mismatch, and fires distclean AGAIN,
# deleting the libtre.a/libxxhash.a call 1 just built. Call 2's own target
# list never includes tre/xxhash, so they're never rebuilt, and the link
# step fails with "cannot find .../deps/{tre,xxhash}/lib*.a".
#
# Fix: pre-seed BOTH deps/.make-cflags and deps/.make-ldflags with the exact
# current CFLAGS/LDFLAGS before the first oe_runmake call, so NEITHER guard
# mismatches on call 1 -- its distclean never fires, so it never touches the
# marker files, so call 2 also finds both markers already matching and never
# fires distclean either. tre/xxhash's artifacts then survive to the link.
do_compile:prepend:qcom-distro() {
    mkdir -p ${S}/deps
    echo "$CFLAGS" > ${S}/deps/.make-cflags
    echo "$LDFLAGS" > ${S}/deps/.make-ldflags
    oe_runmake -C deps tre xxhash
}
