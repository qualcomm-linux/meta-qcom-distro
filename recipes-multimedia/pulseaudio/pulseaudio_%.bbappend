#Split pactl into its own installable package
#This allows adding only the pactl binary without pulling in the pulseaudio daemon

#Prepend pactl-bin to the package list BEFORE pulseaudio-server
#so Yocto assigns /usr/bin/pactl to pactl-bin first
PACKAGES =+ "pactl-bin"

#Assign only the pactl binary to the pactl-bin package
FILES:pactl-bin = "${bindir}/pactl"

#pactl needs libpulse to run — declare the runtime dependency
# - libpulse: The client-side library needed for the binary to run (provided by pulseaudio recipe)
# - pipewire-pulse: The server that pactl needs to communicate with
RDEPENDS:pactl-bin = "libpulse pipewire-pulse"
