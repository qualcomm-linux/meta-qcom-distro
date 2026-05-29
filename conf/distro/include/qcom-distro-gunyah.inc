DISTRO_FEATURES:append = " \
    gunyah \
"

DISTRO_NAME:append = " (Gunyah-enabled)"

# Gunyah and KVM are mutually exclusive
CONFLICT_DISTRO_FEATURES = "kvm"
