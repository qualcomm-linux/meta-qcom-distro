# Temporary workaround until https://lists.openembedded.org/g/openembedded-core/message/232523 is merged
FILES:${PN}:append:qcom-distro = " ${exec_prefix}/lib/nvpcr"
