do_install:append:qcom () {
  install -v -m 0644 \
      ${S}/src/config/wireplumber.conf.d.examples/bluetooth.conf \
      ${D}${datadir}/wireplumber/wireplumber.conf.d/
}

# Enable wireplumber as a system-wide service
SYSTEMD_SERVICE:${PN} = "wireplumber.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
