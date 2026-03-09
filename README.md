# meta-qcom-distro

[![Build on push](https://img.shields.io/github/actions/workflow/status/qualcomm-linux/meta-qcom-distro/push.yml?label=Build%20on%20push)](https://github.com/qualcomm-linux/meta-qcom-distro/actions/workflows/push.yml)

## Introduction

OpenEmbedded/Yocto Project Reference Distro layer for Qualcomm based platforms.

This layer provides a reference distribution configuration and images for Qualcomm
based platforms, for use with OpenEmbedded/Yocto Project Qualcomm BSP layers.

This layer depends on:

```
URI: https://github.com/openembedded/openembedded-core.git
layers: meta
branch: master
revision: HEAD

URI: https://github.com/qualcomm-linux/meta-qcom.git
branch: master
revision: HEAD

```

## Branches

- **main:** Primary development branch, with focus on upstream support and
  compatibility with the most recent Yocto Project release.
- **scarthgap:** Qualcomm Linux >= 1.4, aligned with Yocto Project 5.0 (LTS).
- **kirkstone:** Qualcomm Linux <= 1.3, aligned with Yocto Project 4.0 (LTS).

## Contributing

Please submit any patches against the `meta-qcom-distro` layer by using
the GitHub pull-request feature. Fork the repo, create a branch,
do the work, rebase from upstream, and create the pull request.

For some useful guidelines when submitting patches, please refer to:
[Preparing Changes for Submission](https://docs.yoctoproject.org/dev/contributor-guide/submit-changes.html#preparing-changes-for-submission)

Pull requests will be discussed within the GitHub pull-request infrastructure.

The only branch that is currently open for direct contributions is **main**,
for **scarthgap** and **kirkstone** please raise an issue with the suggested
change instead.


## Communication

- **GitHub Issues:** [meta-qcom-distro issues](https://github.com/qualcomm-linux/meta-qcom-distro/issues)
- **Pull Requests:** [meta-qcom-distro pull requests](https://github.com/qualcomm-linux/meta-qcom-distro/pulls)

## Maintainer(s)

* Sourabh Banerjee <quic_sbanerje@quicinc.com>
* Viswanath Kraleti <quic_vkraleti@quicinc.com>
* Ricardo Salveti <ricardo.salveti@oss.qualcomm.com>
* Nicolas Dechesne <nicolas.dechesne@oss.qualcomm.com>
* Dmitry Baryshkov <dmitry.baryshkov@oss.qualcomm.com>

## License

This layer is licensed under the MIT license. Check out [COPYING.MIT](COPYING.MIT)
for more detais.
