# WebRTC is not enabled by default in the recipe due to its dependencies
# (srt, libsrtp, libnice) are provided by the meta-oe layer
PACKAGECONFIG:append:qcom-distro = " sctp srt srtp webrtc"
