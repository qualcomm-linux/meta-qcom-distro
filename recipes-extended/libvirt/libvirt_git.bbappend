# Temporarily disable warnings due deprecated libxml2 APIs until the proper fix
# gets backported and merged in meta-virtualization
CFLAGS:append:qcom-distro = " -Wno-deprecated-declarations"

# netcf support fails to build with gnulib v202601.
# Temporarily disable it in PACKAGECONFIG to allow libvirt compilation.
PACKAGECONFIG:remove = "netcf"
