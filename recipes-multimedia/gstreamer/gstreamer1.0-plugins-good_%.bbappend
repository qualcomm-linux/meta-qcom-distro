# gtk is an optional feature from the recipe and not enabled by default
# gtk plugins are needed for UI-based sample apps
PACKAGECONFIG:append:qcom-distro = " gtk"
