python __anonymous() {
    if "qcom-distro" in d.getVar("DISTROOVERRIDES").split(":"):
        d.setVarFlag("PACKAGECONFIG", "suiteb", ",,")
        d.setVarFlag("PACKAGECONFIG", "wnm", ",,")
        d.setVarFlag("PACKAGECONFIG", "mbo", ",,")
}

PACKAGECONFIG:append:qcom-distro = " suiteb wnm mbo"
