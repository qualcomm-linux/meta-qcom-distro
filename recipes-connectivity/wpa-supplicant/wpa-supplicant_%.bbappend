# use latest version from the lts-connectivity-mixin layer
DEFAULT_PREFERENCE:layer-lts-connectivity-mixin:qcom-distro = "1"

PACKAGECONFIG:append:qcom-distro = " suiteb wnm mbo"
