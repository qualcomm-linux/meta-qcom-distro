# Agent Guide for meta-qcom-distro

This file guides automation agents to run builds / checks the same way CI does:

- use **kas-container** (isolated from host),
- keep `DL_DIR` and `SSTATE_DIR` outside the repo so caches are shared,
- run `yocto-patchreview` routinely, and run `yocto-check-layer` before
  opening/updating a PR, via the CI helper scripts.

## Project Overview

meta-qcom-distro is the OpenEmbedded / Yocto Project reference distribution layer
for Qualcomm based platforms. It provides the `qcom-distro` DISTRO configuration
and reference images, and depends on the `meta-qcom` BSP layer. The two layers
are maintained as separate repositories.

## 1) Prerequisites

1. `kas-container` available on PATH, or set `KAS_CONTAINER=/abs/path/to/kas-container`
   (from [kas-container](https://github.com/siemens/kas/blob/master/kas-container)).
2. Container runtime access (Docker/Podman backend used by `kas-container`).
3. Work directories outside the repository for build outputs and shared caches.

### Container runtime smoke test (required order)

Run Docker first:

```sh
docker run --rm hello-world
```

Then check Podman:

```sh
if command -v podman >/dev/null 2>&1; then
  podman run --rm hello-world
else
  echo "podman not installed; continue with Docker backend"
fi
```

Notes:

- Do not use `sudo` unless the host setup explicitly requires it.
- Do not create or modify user groups as part of this workflow.
- If Podman is unavailable, Docker-only operation is acceptable.

## 2) Recommended environment

If `KAS_WORK_DIR`, `DL_DIR`, and `SSTATE_DIR` are already set in the environment, use them
directly — do not override them. Only set defaults when they are absent:

```sh
export REPO_DIR="$(pwd)"                               # meta-qcom-distro checkout
export KAS_WORK_DIR="${KAS_WORK_DIR:-/path/to/kas-work}"      # outside repo to avoid polling the checkout
export DL_DIR="${DL_DIR:-/path/to/shared-cache/downloads}"
export SSTATE_DIR="${SSTATE_DIR:-/path/to/shared-cache/sstate-cache}"
mkdir -p "${DL_DIR}" "${SSTATE_DIR}" "${KAS_WORK_DIR}"
```

## 3) Build with kas-container (CI style)

CI build composition always pairs a machine config with the distro config:
`ci/<machine>.yml:ci/qcom-distro.yml[:ci/<feature>.yml]`

- `ci/<machine>.yml` selects the target machine (e.g. `ci/rb3gen2-core-kit.yml`,
  `ci/qcom-armv8a.yml`, `ci/glymur-crd.yml`) and includes `ci/base.yml`, which
  pulls in the `meta-qcom` BSP layer (branch `master`). A machine config is
  required for any build, and replaces `ci/base.yml` in the composition.
- `ci/qcom-distro.yml` sets `distro: qcom-distro`, adds the distro's dependency
  layers (meta-openembedded, meta-ai, meta-virtualization, meta-audioreach,
  meta-selinux, meta-updater, meta-security, meta-dpdk), and defines the default
  image targets (`qcom-multimedia-image`, `qcom-multimedia-proprietary-image`,
  `qcom-container-orchestration-image`, `qcom-networking-image`).

Optional feature fragments (e.g. `ci/performance.yml`, `ci/linux-qcom-6.18.yml`)
can be appended to the composition string.

Example:

```sh
export KAS_YAMLS="ci/rb3gen2-core-kit.yml:ci/qcom-distro.yml"
"${KAS_CONTAINER:-kas-container}" build "${KAS_YAMLS}"
```

## 4) Run routine checks via CI helper scripts

`ci/kas-container-shell-helper.sh` enters a kas shell composed from
`ci/base.yml:ci/qcom-distro.yml` and runs the given script with `/repo /work`.

For routine local validation, run:

```sh
ci/kas-container-shell-helper.sh ci/yocto-patchreview.sh
```

Run `yocto-check-layer` only before opening/updating a pull request:

```sh
ci/kas-container-shell-helper.sh ci/yocto-check-layer.sh
```

Optionally, profile build timings with:

```sh
ci/kas-container-shell-helper.sh ci/yocto-buildstats.sh
```

## 5) Direct kas shell alternative (no helper wrapper)

For one-off commands (a machine config is required, e.g. `ci/qcom-armv8a.yml`):

```sh
kas-container shell --skip repos_checkout ci/qcom-armv8a.yml:ci/qcom-distro.yml -c "bitbake <target>"
```

Use the helper scripts for CI parity whenever possible.

## 6) Pull request / contribution workflow

Follow the repository `README.md` contribution flow:

1. Target branch: **main** for current development, or **wrynose** for the active
   LTS branch (Qualcomm Linux 2.x, aligned with Yocto Project 6.0 LTS). CI builds
   PRs against both.
2. Fork `qualcomm-linux/meta-qcom-distro`, create a topic branch, implement changes.
3. Rebase on latest upstream `main` (or `wrynose` when targeting LTS).
4. Open a GitHub pull request.
5. Use PR discussion for review iteration.

Direct contributions are accepted on **main** and **wrynose**. For the older LTS
branches **scarthgap** and **kirkstone**, raise an issue with the suggested change
instead.

Important:

- Follow Yocto submission guidance referenced in README:
  [Preparing Changes for Submission](https://docs.yoctoproject.org/dev/contributor-guide/submit-changes.html#preparing-changes-for-submission)

Before opening/updating a PR, run CI-equivalent checks in this order:

```sh
ci/kas-container-shell-helper.sh ci/yocto-patchreview.sh
ci/kas-container-shell-helper.sh ci/yocto-check-layer.sh
```

## 7) Commit message best practices (project style)

Use the style seen in recent history:

- `component: imperative summary` (preferred when scoped), e.g.
  - `libvirt: drop qemu PACKAGECONFIG on 32-bit arm hosts (#367)`
  - `ci/qcom-distro.yml: Enable meta-ai layer (#342)`
  - `qcom-minimal-image: enable zram swap by default`
- Or concise imperative summary when cross-cutting.

Every commit **must** include a `Signed-off-by` trailer using the identity from
the local git configuration:

```sh
git commit -s   # or pass --signoff; fetches user.name / user.email from git config
```

If committing programmatically, append the trailer explicitly:

```text
Signed-off-by: $(git config user.name) <$(git config user.email)>
```

Never fabricate a name or email; always read from `git config`.

Guidelines:

- Keep subject line short and specific; capture intent, not a file-by-file dump.
- Use imperative mood (`Add`, `Update`, `Drop`, `Enable`, `Revert`).
- Add a body for non-trivial changes explaining **why** and key design decisions.
- Wrap body lines for readability (~72 chars).
- Use consistent recipe bump wording for version updates, e.g.
  `recipe-name: Update to vX.Y.Z`.
- Include PR reference in subject when appropriate: `(#NNN)`.
- Avoid mixing unrelated changes in one commit; split logically.
- Each patch must be logically coherent, self-contained, and independently buildable.
- The tree must remain in a functional state after every commit.
- Fixups within the same patch series are not allowed; changes should be corrected in the patch where they are introduced.
