#!/usr/bin/env bash
#####################################################################
#   AVD MagicMaskInit Setup
#####################################################################
#
# Support API level: 23 - 33 (21 and 22 images do not have SELinux)
#
# With an emulator booted and accessible via ADB, usage:
# ./build.py avd_patch path/to/booted/avd-image/ramdisk.img
#
# The purpose of this script is to patch AVD ramdisk.img and do a
# full integration test of magicmaskinit under several circumstances.
# After patching ramdisk.img, close the emulator, then select
# "Cold Boot Now" in AVD Manager to force a full reboot.
#
# P.S. If running against the API 28 image, modify init.hpp and set
# ENABLE_AVD_HACK to 1 to enable special hacks designed specifically
# for this use case.
#
#####################################################################
# AVD Init Configurations:
#
# rootfs w/o early mount: API 23 - 25
# rootfs with early mount: API 26 - 27
# Legacy system-as-root: API 28
# 2 stage init: API 29 - 33
#####################################################################

if [ ! -f /system/build.prop ]; then
  # Running on PC
  echo 'Please run `./build.py avd_patch` instead of directly executing the script!'
  exit 1
fi

cd /data/local/tmp
chmod 755 busybox

if [ -z "$FIRST_STAGE" ]; then
  export FIRST_STAGE=1
  export ASH_STANDALONE=1
  # Re-exec script with busybox
  exec ./busybox sh $0
fi

# Extract files from APK
unzip -oj magicmask.apk 'assets/util_functions.sh' 'assets/stub.apk'
. ./util_functions.sh

api_level_arch_detect

unzip -oj magicmask.apk "lib/$ABI/*" "lib/$ABI32/libmagicmask32.so" -x "lib/$ABI/libbusybox.so"
for file in lib*.so; do
  chmod 755 $file
  mv "$file" "${file:3:${#file}-6}"
done

./magicmaskboot decompress ramdisk.cpio.tmp ramdisk.cpio
cp ramdisk.cpio ramdisk.cpio.orig

touch config

echo "RULESDEVICE=$(ISENCRYPTED=true ./magicmaskinit --rules-device)" >> config

# For API 28, we also patch advancedFeatures.ini to disable SAR
# Manually override skip_initramfs by setting RECOVERYMODE=true
[ $API = "28" ] && echo 'RECOVERYMODE=true' >> config

./magicmaskboot compress=xz magicmask32 magicmask32.xz
./magicmaskboot compress=xz magicmask64 magicmask64.xz
./magicmaskboot compress=xz stub.apk stub.xz

export KEEPVERITY=false
export KEEPFORCEENCRYPT=true

./magicmaskboot cpio ramdisk.cpio \
"add 0750 init magicmaskinit" \
"mkdir 0750 overlay.d" \
"mkdir 0750 overlay.d/sbin" \
"add 0644 overlay.d/sbin/magicmask32.xz magicmask32.xz" \
"add 0644 overlay.d/sbin/magicmask64.xz magicmask64.xz" \
"add 0644 overlay.d/sbin/stub.xz stub.xz" \
"patch" \
"backup ramdisk.cpio.orig" \
"mkdir 000 .backup" \
"add 000 .backup/.magicmask config"

rm -f ramdisk.cpio.orig config magicmask*.xz stub.xz
./magicmaskboot compress=gzip ramdisk.cpio ramdisk.cpio.gz
pm install magicmask.apk || true
