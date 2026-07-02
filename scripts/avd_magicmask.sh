#!/usr/bin/env bash
#####################################################################
#   AVD MagicMask Setup
#####################################################################
#
# Support API level: 21 - 33
#
# With an emulator booted and accessible via ADB, usage:
# ./build.py emulator
#
# This script will stop zygote, simulate the MagicMask start up process
# that would've happened before zygote was started, and finally
# restart zygote. This is useful for setting up the emulator for
# developing MagicMask, testing modules, and developing root apps using
# the official Android emulator (AVD) instead of a real device.
#
# This only covers the "core" features of MagicMask. For testing
# magicmaskinit, please checkout avd_patch.sh.
#
#####################################################################

mount_sbin() {
  mount -t tmpfs -o 'mode=0755' magicmask /sbin
  chcon u:object_r:rootfs:s0 /sbin
}

if [ ! -f /system/build.prop ]; then
  # Running on PC
  echo 'Please run `./build.py emulator` instead of directly executing the script!'
  exit 1
fi

cd /data/local/tmp
chmod 755 busybox

if [ -z "$FIRST_STAGE" ]; then
  export FIRST_STAGE=1
  export ASH_STANDALONE=1
  if [ $(./busybox id -u) -ne 0 ]; then
    # Re-exec script with root
    exec /system/xbin/su 0 ./busybox sh $0
  else
    # Re-exec script with busybox
    exec ./busybox sh $0
  fi
fi

pm install -r $(pwd)/magicmask.apk

# Extract files from APK
unzip -oj magicmask.apk 'assets/util_functions.sh' 'assets/stub.apk'
. ./util_functions.sh

api_level_arch_detect

unzip -oj magicmask.apk "lib/$ABI/*" "lib/$ABI32/libmagicmask32.so" -x "lib/$ABI/libbusybox.so"
for file in lib*.so; do
  chmod 755 $file
  mv "$file" "${file:3:${#file}-6}"
done

# Stop zygote (and previous setup if exists)
magicmask --stop 2>/dev/null
stop
if [ -d /dev/avd-magicmask ]; then
  umount -l /dev/avd-magicmask 2>/dev/null
  rm -rf /dev/avd-magicmask 2>/dev/null
fi

# SELinux stuffs
if [ -d /sys/fs/selinux ]; then
  if [ -f /vendor/etc/selinux/precompiled_sepolicy ]; then
    ./magicmaskpolicy --load /vendor/etc/selinux/precompiled_sepolicy --live --magicmask 2>&1
  elif [ -f /sepolicy ]; then
    ./magicmaskpolicy --load /sepolicy --live --magicmask 2>&1
  else
    ./magicmaskpolicy --live --magicmask 2>&1
  fi
fi

MAGICMASKTMP=/sbin

# Setup bin overlay
if mount | grep -q rootfs; then
  # Legacy rootfs
  mount -o rw,remount /
  rm -rf /root
  mkdir /root
  chmod 750 /root
  ln /sbin/* /root
  mount -o ro,remount /
  mount_sbin
  ln -s /root/* /sbin
elif [ -e /sbin ]; then
  # Legacy SAR
  mount_sbin
  mkdir -p /dev/sysroot
  block=$(mount | grep ' / ' | awk '{ print $1 }')
  [ $block = "/dev/root" ] && block=/dev/block/dm-0
  mount -o ro $block /dev/sysroot
  for file in /dev/sysroot/sbin/*; do
    [ ! -e $file ] && break
    if [ -L $file ]; then
      cp -af $file /sbin
    else
      sfile=/sbin/$(basename $file)
      touch $sfile
      mount -o bind $file $sfile
    fi
  done
  umount -l /dev/sysroot
  rm -rf /dev/sysroot
else
  # Android Q+ without sbin
  MAGICMASKTMP=/dev/avd-magicmask
  mkdir /dev/avd-magicmask
  mount -t tmpfs -o 'mode=0755' magicmask /dev/avd-magicmask
fi

# MagicMask stuff
mkdir -p $MAGICMASKBIN 2>/dev/null
unzip -oj magicmask.apk 'assets/*.sh' -d $MAGICMASKBIN
mkdir $NVBASE/modules 2>/dev/null
mkdir $POSTFSDATAD 2>/dev/null
mkdir $SERVICED 2>/dev/null

for file in magicmask32 magicmask64 magicmaskpolicy stub.apk; do
  chmod 755 ./$file
  cp -af ./$file $MAGICMASKTMP/$file
  cp -af ./$file $MAGICMASKBIN/$file
done
cp -af ./magicmaskboot $MAGICMASKBIN/magicmaskboot
cp -af ./magicmaskinit $MAGICMASKBIN/magicmaskinit
cp -af ./busybox $MAGICMASKBIN/busybox

if $IS64BIT; then
  ln -s ./magicmask64 $MAGICMASKTMP/magicmask
else
  ln -s ./magicmask32 $MAGICMASKTMP/magicmask
fi
ln -s ./magicmask $MAGICMASKTMP/su
ln -s ./magicmask $MAGICMASKTMP/resetprop
ln -s ./magicmask $MAGICMASKTMP/magicmaskhide
ln -s ./magicmaskpolicy $MAGICMASKTMP/supolicy

mkdir -p $MAGICMASKTMP/.magicmask/mirror
mkdir $MAGICMASKTMP/.magicmask/block
touch $MAGICMASKTMP/.magicmask/config

# Boot up
$MAGICMASKTMP/magicmask --post-fs-data
while [ ! -f /dev/.magicmask_unblock ]; do sleep 1; done
rm /dev/.magicmask_unblock
start
$MAGICMASKTMP/magicmask --service
