DISTRO_FEATURES:append = " \
    kvm \
"

DISTRO_NAME:append = " (KVM-enabled)"

# Gunyah and KVM are mutually exclusive
CONFLICT_DISTRO_FEATURES = "gunyah"
