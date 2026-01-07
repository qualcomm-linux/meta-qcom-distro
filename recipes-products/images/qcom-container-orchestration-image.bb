require qcom-multimedia-proprietary-image.bb

SUMMARY += "(with container orchestration)"

REQUIRED_DISTRO_FEATURES += "virtualization"

CORE_IMAGE_BASE_INSTALL += " \
    kubeadm \
    kubernetes \
    kubernetes-misc \
    packagegroup-containerd \
    packagegroup-oci \
"
