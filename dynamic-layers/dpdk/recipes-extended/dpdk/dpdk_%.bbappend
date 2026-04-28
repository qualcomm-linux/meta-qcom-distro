EXTRA_OEMESON:remove = "-Dexamples=all"
EXTRA_OEMESON:append = " -Dexamples=   \
		         -Ddisable_drivers='net/virtio,net/vmxnet3, \
                         net/ena,net/octeontx,net/thunderx' \
"
