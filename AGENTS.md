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

## Agent skills

Reusable agent skills for the qualcomm-linux projects are maintained in
[qcom-linux-skills](https://github.com/qualcomm-linux/qcom-linux-skills),
in the `SKILL.md` format understood by Claude Code, Codex, Cursor and
similar agents. Several of them cover the workflows described in this file,
such as `qcom-yocto-build-image` (build images with kas-container),
`qcom-yocto-pre-pr-checks` (the CI-parity checks from section 4), and
`qcom-flash-qdl` / `qcom-boot-validate` (flash and boot-test a board).
Install them with the repository's `install.sh` and prefer an existing
skill over re-deriving the workflow; improvements go back to that catalog.

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

Follow the contribution workflow documented in
[CONTRIBUTING.md](CONTRIBUTING.md):

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

Before opening/updating a PR, run CI-equivalent checks in this order:

```sh
ci/kas-container-shell-helper.sh ci/yocto-patchreview.sh
ci/kas-container-shell-helper.sh ci/yocto-check-layer.sh
```

## 7) Commit message best practices (project style)

Follow the commit subject and message requirements documented in
[CONTRIBUTING.md](CONTRIBUTING.md): an atomic change per commit, a
`recipe-name: summary of the changes` subject, a plain-English body that
explains the problem before the imperative actions, and the mandatory
`Signed-off-by` (and, when applicable, `Assisted-by`) trailers.

When committing programmatically, take the `Signed-off-by` identity from the
local git configuration and append the trailer explicitly:

```text
Signed-off-by: $(git config user.name) <$(git config user.email)>
```

Never fabricate a name or email; always read them from `git config`.

Trailer order matters: `Assisted-by` goes **before** `Signed-off-by`, so the
sign-off is always the last trailer written by the author. A complete
agent-assisted commit message looks like this:

```text
recipe-name: summary of the changes

Explain the problem first, then the change, in plain English.

Assisted-by: AGENT_NAME:MODEL_VERSION
Signed-off-by: Author Name <author@example.com>
```

Do not append `Assisted-by` after `Signed-off-by` (for example with
`git commit -s` followed by `git interpret-trailers --trailer Assisted-by=...`);
write both trailers in the order above in a single commit message instead.

Fixups within the same patch series are not allowed; changes should be
corrected in the patch where they are introduced.

## 8) Backporting to a release branch

Fixes land on `main` first and are then backported to the release branch
(currently `wrynose`). Merged pull requests labelled `backport wrynose` are
backported automatically by `.github/workflows/backport.yml`; when a manual
backport is needed (conflicts, or a change that only applies to the release
branch), follow the same conventions the automation uses:

1. Create a topic branch from the latest release branch, for example
   `backport/<pr-number>-to-wrynose`.
2. Cherry-pick the original commits with `git cherry-pick -x <sha>`, which
   appends the `(cherry picked from commit <sha>)` line for you. Keep the
   original subject, body, and trailers unchanged, and add your own
   `Signed-off-by` after the cherry-pick line if it is not already present.
3. Open the pull request against the release branch with the subject
   prefixed by the target branch, for example
   `[Backport wrynose] recipe-name: summary of the changes`, and link the
   original pull request in the description.

The `[Backport <branch>]` prefix belongs to the pull request subject only.
The commits themselves are normal patches whose only backport marker is the
`(cherry picked from commit ...)` line; never add the prefix to a commit
subject.
