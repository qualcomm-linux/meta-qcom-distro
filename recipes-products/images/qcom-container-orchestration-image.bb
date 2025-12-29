require qcom-multimedia-proprietary-image.bb

SUMMARY = "An image built on top of multimedia proprietary image for container orchestration features"

CORE_IMAGE_BASE_INSTALL += " \
    kubeadm \
    kubernetes \
    kubernetes-misc \
    packagegroup-containerd \
    packagegroup-oci \
"
