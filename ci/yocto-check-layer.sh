#!/bin/bash -e
# Copyright (c) 2024 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: MIT

if [ -z $1 ] || [ -z $2 ] ; then
    echo "The REPO_DIR or WORK_DIR is empty and it needs to point to the corresponding directories."
    echo "Please run it with:"
    echo " $0 REPO_DIR WORK_DIR"
    exit 1
fi

REPO_DIR="$1"
WORK_DIR="$2"

_is_dir(){
    test -d "$1" && return
    echo "The '$1' is not a directory."
    exit 1
}

_is_dir "$REPO_DIR"
_is_dir "$WORK_DIR"

# Creates a temporary build directory to run the yocto-check-layer
# script to avoid a contaminated environment.
BUILDDIR="$(mktemp -p $WORK_DIR -d -t build-yocto-check-layer-XXXX)"
source $WORK_DIR/oe-core/oe-init-build-env $BUILDDIR
git -c advice.detachedHead=false -c init.defaultBranch=master clone --quiet --shared $REPO_DIR meta-qcom-distro

# Patch dependent layers otherwise breaking yocto-check-layer
bitbake-layers create-layer -a meta-patch
mkdir -p meta-patch/recipes-patch/patch
# Taint (by forced/invalidated task) changed from nostamp(uuid4):foo to nostamp(uuid4):foo
echo 'unset do_install[nostamp]' >> meta-patch/recipes-patch/patch/aide_%.bbappend
# Taint (by forced/invalidated task) changed from nostamp(uuid4):foo to nostamp(uuid4):foo
echo 'unset do_populate_registry[nostamp]' >> meta-patch/recipes-patch/patch/container-registry-populate.bbappend
# QA error because of network access
echo 'unset do_populate_registry[network]' >> meta-patch/recipes-patch/patch/container-registry-populate.bbappend
# Depends on the package outside of the layer set
echo 'EXCLUDE_FROM_WORLD = "1"' >> meta-patch/recipes-patch/patch/cockpit-machines_%.bbappend
# Missing or unbuildable dependency chain was: ['meta-world-pkgdata', 'packagegroup-yocto-builder', 'systemd']
echo 'inherit features_check' >> meta-patch/recipes-patch/patch/packagegroup-yocto-builder.bbappend
echo 'REQUIRED_DISTRO_FEATURES = "systemd"' >> meta-patch/recipes-patch/patch/packagegroup-yocto-builder.bbappend

# DISTRO features of qcom-distro
echo 'DISTRO_FEATURES:append:nodistro = " \
    bluetooth \
    efi \
    glvnd \
    opencl \
    opengl \
    overlayfs \
    pam \
    pni-names \
    ptest \
    security \
    tpm2 \
    virtualization \
    vulkan \
    wayland \
    wifi \
    x11 \
"' >> conf/local.conf

# Yocto Project layer checking tool
CMD="yocto-check-layer"
# Layer to check
CMD="$CMD meta-qcom-distro"
# Disable auto layer discovery
CMD="$CMD --no-auto"
# Layers to process for dependencies
CMD="$CMD --dependency"
CMD="$CMD   $WORK_DIR/oe-core/meta"
CMD="$CMD   $WORK_DIR/meta-qcom"
CMD="$CMD   $WORK_DIR/meta-openembedded/meta-oe"
CMD="$CMD   $WORK_DIR/meta-openembedded/meta-networking"
CMD="$CMD   $WORK_DIR/meta-openembedded/meta-multimedia"
CMD="$CMD   $WORK_DIR/meta-openembedded/meta-python"
CMD="$CMD   $WORK_DIR/meta-openembedded/meta-filesystems"
CMD="$CMD   $WORK_DIR/meta-openembedded/meta-gnome"
CMD="$CMD   $WORK_DIR/meta-openembedded/meta-xfce"
CMD="$CMD   $WORK_DIR/meta-security"
CMD="$CMD   $WORK_DIR/meta-security/meta-tpm"
CMD="$CMD   $WORK_DIR/meta-selinux"
CMD="$CMD   $WORK_DIR/meta-updater"
CMD="$CMD   $WORK_DIR/meta-virtualization"
# Disable automatic testing of dependencies
CMD="$CMD --no-auto-dependency"
# Set machines to all machines defined in this BSP layer
CMD="$CMD --machines $(echo $(find ${WORK_DIR}/meta-qcom/conf/machine/ -maxdepth 1 -name *.conf -exec basename {} .conf \; ))"

exec $CMD
