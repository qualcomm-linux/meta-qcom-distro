# Temporarily disable warnings due deprecated libxml2 APIs until the proper fix
# gets backported and merged in meta-virtualization
CFLAGS:append:qcom-distro = " -Wno-deprecated-declarations"
