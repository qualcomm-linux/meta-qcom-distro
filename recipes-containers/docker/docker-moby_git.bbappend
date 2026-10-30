SRCREV_moby = "9bad21ba35b971ab4c491f4338eb8fb3dd982ecc"
SRCREV_cli = "28f756087eea5fa301f3fbf12b01ae62f91521c2"

SRC_URI:remove = "\
        file://CVE-2026-41568.patch;patchdir=src/import \
        file://CVE-2026-42306_p1.patch;patchdir=src/import \
        file://CVE-2026-42306_p2.patch;patchdir=src/import \
	"

DOCKER_VERSION = "v29.7.2"
