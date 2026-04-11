PACKAGECONFIG:append:qcom-distro = " ${@bb.utils.contains('MACHINE_FEATURES', 'phone', ' modemmanager ', '', d)}"
