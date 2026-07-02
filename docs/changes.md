# MagicMask Changelog

### v25.2

- [MagicMaskInit] Fix a potential issue when stub cpio is used
- [MagicMaskInit] Fix reboot to recovery when stub cpio is used
- [MagicMaskInit] Fix sepolicy.rules symlink for rootfs devices
- [General] Better data encryption detection
- [General] Move the whole logging infrastructure into Rust

### v25.1

- [MagicMaskBoot] Fix ramdisk backup being incorrectly skipped
- [MagicMaskBoot] Add new feature to detect unsupported dtb and abort during installation
- [Zygisk] Change binary hijack paths
- [App] Fix incorrect recovery mode detection and installation
- [MagicMaskInit] Fix config not properly exported in legacy SAR devices
- [General] Enforce the MagicMask app to always match or be newer than `magicmaskd`

### v25.0

- [MagicMaskInit] Update 2SI implementation, significantly increase device compatibility (e.g. Sony Xperia devices)
- [MagicMaskInit] Introduce new `sepolicy` injection mechanism
- [MagicMaskInit] Support Oculus Go
- [MagicMaskInit] Support Android 13 GKIs (Pixel 6)
- [MagicMaskBoot] Fix vbmeta extraction implementation
- [App] Fix stub app on older Android versions
- [App] [MagicMaskSU] Properly support apps using `sharedUserId`
- [MagicMaskSU] Fix a possible crash in `magicmaskd`
- [MagicMaskSU] Prune unused UIDs as soon as `system_server` restarts to prevent UID reuse attacks
- [MagicMaskSU] Verify and enforce the installed MagicMask app's certificate to match the distributor's signature
- [MagicMaskSU] [Zygisk] Proper package management and detection
- [Zygisk] Fix function hooking on devices running Android 12 with old kernels
- [Zygisk] Fix Zygisk's self code unloading implementation
- [DenyList] Fix DenyList on shared UID apps
- [BusyBox] Add workaround for devices running old kernels

### v24.3

- [General] Stop using `getrandom` syscall
- [Zygisk] Update API to v3, adding new fields to `AppSpecializeArgs`
- [App] Improve app repackaging installation workflow

### v24.2

- [MagicMaskSU] Fix buffer overflow
- [MagicMaskSU] Fix owner managed multiuser superuser settings
- [MagicMaskSU] Fix command logging when using `su -c <cmd>`
- [MagicMaskSU] Prevent su request indefinite blocking
- [MagicMaskBoot] Support `lz4_legacy` archive with multiple magic
- [MagicMaskBoot] Fix `lz4_lg` compression
- [DenyList] Allow targeting processes running as system UID
- [Zygisk] Workaround Samsung's "early zygote"
- [Zygisk] Improved Zygisk loading mechanism
- [Zygisk] Fix application UID tracking
- [Zygisk] Fix improper `umask` being set in zygote
- [App] Fix BusyBox execution test
- [App] Improve stub loading mechanism
- [App] Major app upgrade flow improvements
- [General] Improve commandline error handling and messaging

### v24.1

- [App] Stability improvements

### v24.0

- [General] MagicMaskHide is removed from MagicMask
- [General] Support Android 12
- [General] Support devices that do not support 32-bit and only runs 64-bit code
- [General] Update BusyBox to 1.34.1
- [Zygisk] Introduce new feature: Zygisk
- [Zygisk] Introduce DenyList feature to revert MagicMask features in user selected processes
- [MagicMaskBoot] Support patching 32-bit kernel zImages
- [MagicMaskBoot] Support boot image header v4
- [MagicMaskBoot] Support patching out `skip_initramfs` from dtb bootargs
- [MagicMaskBoot] Add new env variable `PATCHVBMETAFLAG` to configure whether vbmeta flags should be patched
- [MagicMaskInit] Support loading fstab from `/system/etc` (required for Pixel 6)
- [MagicMaskInit] Support `/proc/bootconfig` for loading boot configurations
- [MagicMaskInit] Better support for some Meizu devices
- [MagicMaskInit] Better support for some OnePlus/Oppo/Realme devices
- [MagicMaskInit] Support `init.real` on some Sony devices
- [MagicMaskInit] Skip loading MagicMask when detecting DSU
- [MagicMaskPolicy] Load `*_compat_cil_file` from system_ext
- [MagicMaskSU] Use isolated devpts if the kernel supports it
- [MagicMaskSU] Fix root shell if isolated mount namespace is set
- [resetprop] Deleted properties are now wiped from memory instead of just unlinking
- [App] Build a single APK for all ABIs
- [App] Switch to use standard bottom navigation bar
- [App] Downloading modules from the centralized MagicMask-Modules-Repo is removed
- [App] Support user configuration of boot image vbmeta patching
- [App] Restore the ability to install MagicMask on the other slot on some A/B devices
- [App] Allow modules to specify an update URL for in-app update + install

### v23.0

- [App] Update snet extension. This fixes SafetyNet API errors.
- [App] Fix a bug in the stub app that causes APK installation to fail
- [App] Hide annoying errors in logs when hidden as stub
- [App] Fix issues when patching ODIN tar files when the app is hidden
- [General] Remove all pre Android 5.0 support
- [General] Update BusyBox to use proper libc
- [General] Fix C++ undefined behaviors
- [General] Several `sepolicy.rule` copy/installation fixes
- [MagicMaskPolicy] Remove unnecessary sepolicy rules
- [MagicMaskHide] Update package and process name validation logic
- [MagicMaskHide] Some changes that prevents zygote deadlock

### v22.1

- [App] Prevent multiple installation sessions running in parallel
- [App] Prevent OutOfMemory crashes when checking boot signature on PXA boot images
- [General] Proper cgroup migration implementation
- [General] Rewrite log writer from scratch, should resolve any crashes and deadlocks
- [General] Many scripts updates fixing regressions
- [MagicMaskHide] Prevent possible deadlock when signal arrives
- [MagicMaskHide] Partial match process names if necessary
- [MagicMaskBoot] Preserve and patch AVB 2.0 structures/headers in boot images
- [MagicMaskBoot] Properly strip out data encryption flags
- [MagicMaskBoot] Prevent possible integer overflow
- [MagicMaskInit] Fix `sepolicy.rule` mounting strategy
- [resetprop] Always delete existing `ro.` props before updating. This will fix bootloops that could be caused by modifying device fingerprint properties.

### v22.0

- [General] MagicMask and MagicMask Manager is now merged into the same package!
- [App] The term "MagicMask Manager" is no longer used elsewhere. We refer it as the MagicMask app.
- [App] Support hiding the MagicMask app with advanced technique (stub APK loading) on Android 5.0+ (it used to be 9.0+)
- [App] Disallow re-packaging the MagicMask app on devices lower than Android 5.0
- [App] Detect and warn about multiple invalid states and provide instructions on how to resolve it
- [MagicMaskHide] Fix a bug when stopping MagicMaskHide does not take effect
- [MagicMaskBoot] Fix bug when unpacking `lz4_lg` compressed boot images
- [MagicMaskInit] Support Galaxy S21 series
- [MagicMaskSU] Fix incorrect APEX paths that caused `libsqlite.so` fail to load

### v21.4

- [MagicMaskSU] Fix `su -c` behavior that broke many root apps
- [General] Properly handle read/write over sockets (the `broken pipe` issue)

### v21.3

- [MagicMaskInit] Avoid mounting `f2fs` userdata as it may result in kernel crashes. This shall fix a lot of bootloops
- [MagicMaskBoot] Fix a minor header checksum bug for `DHTB` header and ASUS `blob` image formats
- [MagicMaskHide] Allowing hiding isolated processes if the mount namespace is separated

### v21.2

- [MagicMaskInit] Detect 2SI after mounting `system_root` on legacy SAR devices
- [General] Make sure `post-fs-data` scripts cannot block more than 35 seconds
- [General] Fix the `magicmask --install-module` command
- [General] Trim Windows newline when reading files
- [General] Directly log to file to prevent `logcat` weirdness
- [MagicMaskBoot] Fix header dump/load for header v3 images

### v21.1

- [MagicMaskBoot] Support boot header v3 (Pixel 5 and 4a 5G)
- [MagicMaskBoot] Distinguish `lz4_lg` and `lz4_legacy` (Pixel 5 and 4a 5G)
- [MagicMaskBoot] Support vendor boot images (for dev, not relevant for MagicMask installation)
- [MagicMaskInit] Support kernel cmdline `androidboot.fstab_suffix`
- [MagicMaskInit] Support kernel initialized dm-verity on legacy SAR
- [General] Significantly broaden sepolicy.rule compatibility
- [General] Add MagicMask binaries to `PATH` when executing boot scripts
- [General] Update `--remove-modules` command implementation
- [General] Make MagicMask properly survive after factory reset on Android 11
- [MagicMaskSU] Add APEX package `com.android.i18n` to `LD_LIBRARY_PATH` when linking `libsqlite.so`
- [MagicMaskHide] Support hiding apps installed in secondary users (e.g. work profile)
- [MagicMaskHide] Make zygote detection more robust

### v21.0

- [General] Support Android 11 🎉
- [General] Add Safe Mode detection. Disable all modules when the device is booting into Safe Mode.
- [General] Increase `post-fs-data` mode timeout from 10 seconds to 40 seconds
- [MagicMaskInit] Rewritten 2SI support from scratch
- [MagicMaskInit] Support when no `/sbin` folder exists (Android 11)
- [MagicMaskInit] Dump fstab from device-tree to rootfs and force `init` to use it for 2SI devices
- [MagicMaskInit] Strip out AVB for 2SI as it may cause bootloop
- [Modules] Rewritten module mounting logic from scratch
- [MagicMaskSU] For Android 8.0+, a completely new policy setup is used. This reduces compromises in Android's sandbox, providing more policy isolation and better security for root users.
- [MagicMaskSU] Isolated mount namespace will now first inherit from parent process, then isolate itself from the world
- [MagicMaskSU] Update communication protocol with MagicMask Manager to work with the hardened SELinux setup
- [MagicMaskPolicy] Optimize match all rules. This will significantly reduce policy binary size and save memory and improve general kernel performance.
- [MagicMaskPolicy] Support declaring new types and attributes
- [MagicMaskPolicy] Make policy statement closer to stock `*.te` format. Please check updated documentation or `magicmaskpolicy --help` for more details.
- [MagicMaskBoot] Support compressed `extra` blobs
- [MagicMaskBoot] Pad boot images to original size with zeros
- [MagicMaskHide] Manipulate additional vendor properties

### v20.4

- [MagicMaskInit] Fix potential bootloop in A-only 2SI devices
- [MagicMaskInit] Properly support Tegra partition naming
- [General] Load libsqlite.so dynamically, which removes the need to use wrapper scripts on Android 10+
- [General] Detect API level with a fallback method on some devices
- [General] Workaround possible bug in x86 kernel readlinkat system call
- [BusyBox] Enable SELinux features. Add chcon/runcon etc., and '-Z' option to many applets
- [BusyBox] Introduce standalone mode. More details in release notes
- [MagicMaskHide] Disable MagicMaskHide by default
- [MagicMaskHide] Add more potential detectable system properties
- [MagicMaskHide] Add workaround for Xiaomi devices bootloop when MagicMaskHide is enabled on cross region ROMs
- [MagicMaskBoot] Support patching special Motorolla DTB format
- [MagicMaskPolicy] Support 'genfscon' sepolicy rules
- [Scripts] Support NAND based boot images (character nodes in /dev/block)
- [Scripts] Better addon.d (both v1 and v2) support
- [Scripts] Support Lineage Recovery for Android 10+

### v20.3

- [MagicMaskBoot] Fix `lz4_legacy` decompression

### v20.2

- [MagicMaskSU] Properly handle communication between daemon and application (root request prompt)
- [MagicMaskInit] Fix logging in kmsg
- [MagicMaskBoot] Support patching dtb/dtbo partition formats
- [General] Support pre-init sepolicy patch in modules
- [Scripts] Update magicmask stock image backup format

### v20.1

- [MagicMaskSU] Support component name agnostic communication (for stub APK)
- [MagicMaskBoot] Set proper `header_size` in boot image headers (fix vbmeta error on Samsung devices)
- [MagicMaskHide] Scan zygote multiple times
- [MagicMaskInit] Support recovery images without /sbin/recovery binary. This will fix some A/B devices unable to boot to recovery after flashing MagicMask
- [General] Move acct to prevent daemon being killed
- [General] Make sure "--remove-modules" will execute uninstall.sh after removal

### v20.0

- [MagicMaskBoot] Support inject/modify `mnt_point` value in DTB fstab
- [MagicMaskBoot] Support patching QCDT
- [MagicMaskBoot] Support patching DTBH
- [MagicMaskBoot] Support patching PXA-DT
- [MagicMaskInit] [2SI] Support non A/B setup (Android 10)
- [MagicMaskHide] Fix bug that reject process names with ":"
- [MagicMount] Fix a bug that cause /product mirror not created

### v19.4

- [MagicMaskInit] [SAR] Boot system-as-root devices with system mounted as /
- [MagicMaskInit] [2SI] Support 2-stage-init for A/B devices (Pixel 3 Android 10)
- [MagicMaskInit] [initramfs] Delay sbin overlay creation to post-fs-data
- [MagicMaskInit] [SARCompat] Old system-as-root implementation is deprecated, no more future changes
- [MagicMaskInit] Add overlay.d support for root directory overlay for new system-as-root implementation
- [MagicMaskSU] Unblock all signals in root shells (fix bash on Android)
- [MagicMount] Support replacing files in /product
- [MagicMaskHide] Support Android 10's Zygote blastula pool
- [MagicMaskHide] All random strings now also have random length
- [MagicMaskBoot] Allow no recompression for ramdisk.cpio
- [MagicMaskBoot] Support some weird Huawei boot images
- [General] Add new `--remove-modules` command to remove modules without root in ADB shell
- [General] Support Android 10 new APEX libraries (Project Mainline)

### v19.3

- [MagicMaskHide] Hugely improve process monitor implementation, hopefully should no longer cause 100% CPU and daemon crashes
- [MagicMaskInit] Wait for partitions to be ready for early mount, should fix bootloops on a handful of devices
- [MagicMaskInit] Support EROFS used in EMUI 9.1
- [MagicMaskSU] Properly implement mount namespace isolation
- [MagicMaskBoot] Proper checksum calculation for header v2

### v19.2

- [General] Fix uninstaller
- [General] Fix bootloops on some devices with tmpfs mounting to /data
- [MagicMaskInit] Add Kirin hi6250 support
- [MagicMaskSU] Stop claiming device focus for su logging/notify if feasible.
  This fix issues with users locking MagicMask Manager with app lock, and prevent
  video apps get messed up when an app is requesting root in the background.

### v19.1

- [General] Support recovery based MagicMask
- [General] Support Android Q Beta 2
- [MagicMaskInit] New sbin overlay setup process for better compatibility
- [MagicMaskInit] Allow long pressing volume up to boot to recovery in recovery mode
- [MagicMount] Use proper system_root mirror
- [MagicMount] Use self created device nodes for mirrors
- [MagicMount] Do not allow adding new files/folders in partition root folder (e.g. /system or /vendor)

### v19.0

- [General] Remove usage of magicmask.img
- [General] Add 64 bit magicmask binary for native 64 bit support
- [General] Support A only system-as-root devices that released with Android 9.0
- [General] Support non EXT4 system and vendor partitions
- [MagicMaskHide] Use Zygote ptracing for monitoring new processes
- [MagicMaskHide] Targets are now per-application component
- [MagicMaskInit] Support Android Q (no logical partition support yet!)
- [MagicMaskPolicy] Support Android Q new split sepolicy setup
- [MagicMaskInit] Move sbin overlay creation from main daemon post-fs-data to early-init
- [General] Service scripts now run in parallel
- [MagicMaskInit] Directly inject magicmask services to init.rc
- [General] Use lzma2 compressed ramdisk in extreme conditions
- [MagicMount] Clone attributes from original file if exists
- [MagicMaskSU] Use `ACTION_REBOOT` intent to workaround some OEM broadcast restrictions
- [General] Use `skip_mount` instead of `auto_mount`: from opt-in to opt-out

### v18.1

- [General] Support EMUI 9.0
- [General] Support Kirin 960 devices
- [General] Support down to Android 4.2
- [General] Major code base modernization under-the-hood

### v18.0

- [General] Migrate all code base to C++
- [General] Modify database natively instead of going through MagicMask Manager
- [General] Deprecate path /sbin/.core, please start using /sbin/.magicmask
- [General] Boot scripts are moved from `<magicmask_img>/.core/<stage>.d` to `/data/adb/<stage>.d`
- [General] Remove native systemless hosts (MagicMask Manager is updated with a built-in systemless hosts module)
- [General] Allow module post-fs-data.sh scripts to disable/remove modules
- [MagicMaskHide] Use component names instead of process names as targets
- [MagicMaskHide] Add procfs protection on SDK 24+ (Nougat)
- [MagicMaskHide] Remove the folder /.backup to prevent detection
- [MagicMaskHide] Hide list is now stored in database instead of raw textfile in images
- [MagicMaskHide] Add "--status" option to CLI
- [MagicMaskHide] Stop unmounting non-custom related mount points
- [MagicMaskSU] Add `FLAG_INCLUDE_STOPPED_PACKAGES` in broadcasts to force wake MagicMask Manager
- [MagicMaskSU] Fix a bug causing SIGWINCH not properly detected
- [MagicMaskPolicy] Support new av rules: type_change, type_member
- [MagicMaskPolicy] Remove all AUDITDENY rules after patching sepolicy to log all denies for debugging
- [MagicMaskBoot] Properly support extra_cmdline in boot headers
- [MagicMaskBoot] Try to repair broken v1 boot image headers
- [MagicMaskBoot] Add new CPIO command: "exists"

### v17.3

- [MagicMaskBoot] Support boot image header v1 (Pixel 3)
- [MagicMaskSU] No more linked lists for caching `su_info`
- [MagicMaskSU] Parse command-lines in client side and send only options to daemon
- [MagicMaskSU] Early ACK to prevent client freezes and early denies
- [Daemon] Prevent bootloops in situations where /data is mounted twice
- [Daemon] Prevent logcat failures when /system/bin is magic mounting, could cause MagicMaskHide to fail
- [Scripts] Switch hexpatch to remove Samsung Defex to a more general pattern
- [Scripts] Update data encryption detection for better custom recovery support

### v17.2

- [ResetProp] Update to AOSP upstream to support serialized system properties
- [MagicMaskInit] Randomize MagicMask service names to prevent detection (e.g. FGO)
- [MagicMaskSU] New communication scheme to communicate with MagicMask Manager

### v17.0/17.1

- [General] Bring back install to inactive slot for OTAs on A/B devices
- [Script] Remove system based root in addon.d
- [Script] Add proper addon.d-v2 for preserving MagicMask on custom ROMs on A/B devices
- [Script] Enable KEEPVERITY when the device is using system_root_image
- [Script] Add hexpatch to remove Samsung defex in new Oreo kernels
- [Daemon] Support non ext4 filesystems for mirrors (system/vendor)
- [MagicMaskSU] Make pts sockets always run in dev_pts secontext, providing all terminal emulator root shell the same power as adb shells
- [MagicMaskHide] Kill all processes with same UID of the target to workaround OOS embryo optimization
- [MagicMaskInit] Move all sepolicy patches pre-init to prevent Pixel 2 (XL) boot service breakdown

### v16.7

- [Scripts] Fix boot image patching errors on Android P (workaround the strengthened seccomp)
- [MagicMaskHide] Support hardlink based ns proc mnt (old kernel support)
- [Daemon] Fix permission of /dev/null after logcat commands, fix ADB on EMUI
- [Daemon] Log fatal errors only on debug builds
- [MagicMaskInit] Detect early mount partname from fstab in device tree

### v16.6

- [General] Add wrapper script to overcome weird `LD_XXX` flags set in apps
- [General] Prevent bootloop when flashing MagicMask after full wipe on FBE devices
- [Scripts] Support patching DTB placed in extra sections in boot images (Samsung S9/S9+)
- [Scripts] Add support for addon.d-v2 (untested)
- [Scripts] Fix custom recovery console output in addon.d
- [Scripts] Fallback to parsing sysfs for detecting block devices
- [Daemon] Check whether a valid MagicMask Manager is installed on boot, if not, install stub APK embedded in magicmaskinit
- [Daemon] Check whether MagicMask Manager is repackaged (hidden), and prevent malware from hijacking com.topjohnwu.magicmask
- [Daemon] Introduce new daemon: magicmasklogd, a dedicated daemon to handle all logcat related monitoring
- [Daemon] Replace old invincible mode with handshake between magicmaskd and magicmasklogd, one will respawn the other if disconnected
- [Daemon] Support GSI adbd bind mounting
- [MagicMaskInit] Support detecting block names in upper case (Samsung)
- [MagicMaskBoot] Check DTB headers to prevent false detections within kernel binary
- [MagicMaskHide] Compare mount namespace with PPID to make sure the namespace is actually separated, fix root loss
- [MagicMaskSU] Simplify `su_info` caching system, should use less resources and computing power
- [MagicMaskSU] Reduce the amount of broadcasting to MagicMask Manager
- [ImgTool] Separate all ext4 image related operations to a new applet called "imgtool"
- [ImgTool] Use precise free space calculation methods
- [ImgTool] Use our own set of loop devices hidden along side with sbin tmpfs overlay. This not only eliminates another possible detection method, but also fixes apps that mount OBB files as loop devices (huge thanks to dev of Pzizz for reporting this issue)

### v16.4

- [Daemon] Directly check logcat command instead of detecting logd, should fix logging and MagicMaskHide on several Samsung devices
- [Daemon] Fix startup MagicMask Manager APK installation on Android P
- [MagicMaskPolicy] Switch from AOSP u:r:su:s0 to u:r:magicmask:s0 to prevent conflicts
- [MagicMaskPolicy] Remove unnecessary sepolicy rules to reduce security penalty
- [Daemon] Massive re-design /sbin tmpfs overlay and daemon start up
- [MagicMaskInit] Remove `magicmaskinit_daemon`, the actual magicmask daemon (magicmaskd) shall handle everything itself
- [Daemon] Remove post-fs stage as it is very limited and also will not work on A/B devices; replaced with simple mount in post-fs-data, which will run ASAP even before the daemon is started
- [General] Remove all 64-bit binaries as there is no point in using them; all binaries are now 32-bit only.
  Some weirdly implemented root apps might break (e.g. Tasker, already reported to the developer), but it is not my fault :)
- [resetprop] Add Protobuf encode/decode to support manipulating persist properties on Android P
- [MagicMaskHide] Include app sub-services as hiding targets. This might significantly increase the amount of apps that could be properly hidden

### v16.3

- [General] Remove symlinks used for backwards compatibility
- [MagicMaskBoot] Fix a small size calculation bug

### v16.2

- [General] Force use system binaries in handling ext4 images (fix module installation on Android P)
- [MagicMaskHide] Change property state to disable if logd is disabled

### v16.1

- [MagicMaskBoot] Fix MTK boot image packaging
- [MagicMaskBoot] Add more Nook/Acclaim headers support
- [MagicMaskBoot] Support unpacking DTB with empty kernel image
- [MagicMaskBoot] Update high compression mode detection logic
- [Daemon] Support new mke2fs tool on Android P
- [resetprop] Support Android P new property context files
- [MagicMaskPolicy] Add new rules for Android P

### v16.0

- [MagicMaskInit] Support non `skip_initramfs` devices with slot suffix (Huawei Treble)
- [MagicMaskPolicy] Add rules for MagicMask Manager
- [Compiler] Workaround an NDK compiler bug that causes bootloops

### v15.4

- [MagicMaskBoot] Support Samsung PXA, DHTB header images
- [MagicMaskBoot] Support ASUS blob images
- [MagicMaskBoot] Support Nook Green Loader images
- [MagicMaskBoot] Support pure ramdisk images
- [MagicMaskInit] Prevent OnePlus angela `sepolicy_debug` from loading
- [MagicMaskInit] Obfuscate MagicMask socket entry to prevent detection and security
- [Daemon] Fix subfolders in /sbin shadowed by overlay
- [Daemon] Obfuscate binary names to prevent naive detections
- [Daemon] Check logd before force trying to start logcat in a loop

### v15.3

- [Daemon] Fix the bug that only one script would be executed in post-fs-data.d/service.d
- [Daemon] Add `MS_SILENT` flag when mounting, should fix some devices that cannot mount magicmask.img
- [MagicMaskBoot] Fix potential segmentation fault when patching ramdisk, should fix some installation failures

### v15.2

- [MagicMaskBoot] Fix dtb verity patches, should fix dm-verity bootloops on newer devices placing fstabs in dtb
- [MagicMaskPolicy] Add new rules for proper Samsung support, should fix MagicMaskHide
- [MagicMaskInit] Support non `skip_initramfs` devices using split sepolicies (e.g. Zenfone 4 Oreo)
- [Daemon] Use specific logcat buffers, some devices does not support all log buffers
- [scripts] Update scripts to double check whether boot slot is available, some devices set a boot slot without A/B partitions

### v15.1

- [MagicMaskBoot] Fix faulty code in ramdisk patches which causes bootloops in some config and fstab format combos

### v15.0

- [Daemon] Fix the bug that MagicMask cannot properly detect /data encryption state
- [Daemon] Add merging `/cache/magicmask.img` and `/data/adb/magicmask_merge.img` support
- [Daemon] Update to upstream libsepol to support cutting edge split policy custom ROM cil compilations

### v14.6 (1468)

- [General] Move all files into a safe location: /data/adb
- [Daemon] New invincible implementation: use `magicmaskinit_daemon` to monitor sockets
- [Daemon] Rewrite logcat monitor to be more efficient
- [Daemon] Fix a bug where logcat monitor may spawn infinite logcat processes
- [MagicMaskSU] Update su to work the same as proper Linux implementation:
  Initialize window size; all environment variables will be migrated (except HOME, SHELL, USER, LOGNAME, these will be set accordingly),
  "--preserve-environment" option will preserve all variables, including those four exceptions.
  Check the Linux su manpage for more info
- [MagicMaskBoot] Massive refactor, rewrite all cpio operations and CLI
- [MagicMaskInit][magicmaskboot] Support ramdisk high compression mode

### v14.5 (1456)

- [MagicMaskinit] Fix bootloop issues on several devices
- [misc] Build binaries with NDK r10e, should get rid of the nasty linker warning when executing magicmask

### v14.5 (1455)

- [Daemon] Moved internal path to /sbin/.core, new image mountpoint is /sbin/.core/img
- [MagicMaskSU] Support switching package name, used when MagicMask Manager is hidden
- [MagicMaskHide] Add temporary /magicmask removal
- [MagicMaskHide] All changes above contributes to hiding from nasty apps like FGO and several banking apps
- [MagicMaskinit] Use magicmaskinit for all devices (dynamic initramfs)
- [MagicMaskinit] Fix Xiaomi A1 support
- [MagicMaskinit] Add Pixel 2 (XL) support
- [MagicMaskboot] Add support to remove avb-verity in dtbo.img
- [MagicMaskboot] Fix typo in handling MTK boot image headers
- [script] Along with updates in MagicMask Manager, add support to sign boot images (AVB 1.0)
- [script] Add dtbo.img backup and restore support
- [misc] Many small adjustments to properly support old platforms like Android 5.0

### v14.3 (1437)

- [MagicMaskBoot] Fix Pixel C installation
- [MagicMaskBoot] Handle special `lz4_legacy` format properly, should fix all LG devices
- [Daemon] New universal logcat monitor is added, support plug-and-play to worker threads
- [Daemon] Invincible mode: daemon will be restarted by init, everything should seamlessly through daemon restarts
- [Daemon] Add new restorecon action, will go through and fix all MagicMask files with selinux unlabeled to `system_file` context
- [Daemon] Add brute-force image resizing mode, should prevent the notorious Samsung crappy resize2fs from affecting the result
- [resetprop] Add new "-p" flag, used to toggle whether alter/access the actual persist storage for persist props

### v14.2

- [MagicMount] Clone attributes to tmpfs mountpoint, should fix massive module breakage

### v14.1

- [MagicMaskInit] Introduce a new init binary to support `skip_initramfs` devices (Pixel family)
- [script] Fix typo in update-binary for x86 devices
- [script] Fix stock boot image backup not moved to proper location
- [script] Add functions to support A/B slot and `skip_initramfs` devices
- [script] Detect Meizu boot blocks
- [MagicMaskBoot] Add decompress zImage support
- [MagicMaskBoot] Support extracting dtb appended to zImage block
- [MagicMaskBoot] Support patching fstab within dtb
- [Daemon/MagicMaskSU] Proper file based encryption support
- [Daemon] Create core folders if not exist
- [resetprop] Fix a bug which delete props won't remove persist props not in memory
- [MagicMount] Remove usage of dummy folder, directly mount tmpfs and construct file structure skeleton in place

### v14.0

- [script] Simplify installation scripts
- [script] Fix a bug causing backing up and restoring stock boot images failure
- [script] Installation and uninstallation will migrate old or broken stock boot image backups to proper format
- [script] Fix an issue with selabel setting in `util_functions.sh` on Lollipop
- [rc script] Enable logd in post-fs to start logging as early as possible
- [MagicMaskHide] magicmask.img mounted is no longer a requirement
  Devices with issues mounting magicmask.img can now run in proper core-only mode
- [MagicMaskBoot] Add native function to extract stock SHA1 from ramdisk
- [b64xz] New tool to extract compressed and encoded binary dumps in shell script
- [busybox] Add busybox to MagicMask source, and embed multi-arch busybox binary into update-binary shell script
- [busybox] Busybox is added into PATH for all boot scripts (post-fs-data.d, service.d, and all module scripts)
- [MagicMaskSU] Fully fix multiuser issues
- [Magic Mount] Fix a typo in cloning attributes
- [Daemon] Fix the daemon crashing when boot scripts opens a subshell
- [Daemon] Adjustments to prevent stock Samsung kernel restrictions on exec system calls for binaries started from /data
- [Daemon] Workaround on Samsung device with weird fork behaviors

### v13.3

- [MagicMaskHide] Update to bypass Google CTS (2017.7.17)
- [resetprop] Properly support removing persist props
- [uninstaller] Remove MagicMask Manager and persist props

### v13.2

- [magicmaskpolicy] Fix magicmaskpolicy segfault on old Android versions, should fix tons of older devices that couldn't use v13.1
- [MagicMaskHide] Set proper selinux context while re-linking /sbin to hide MagicMask, should potentially fix many issues
- [MagicMaskBoot] Change lzma compression encoder flag from `LZMA_CHECK_CRC64` to `LZMA_CHECK_CRC32`, kernel only supports latter
- [General] Core-only mode now properly mounts systemless hosts and magicmaskhide

### v13.1

- [General] Merge MagicMaskSU, magicmaskhide, resetprop, magicmaskpolicy into one binary
- [General] Add Android O support (tested on DP3)
- [General] Dynamic link libselinux.so, libsqlite.so from system to greatly reduce binary size
- [General] Remove bundled busybox because it causes a lot of issues
- [General] Unlock all block devices for read-write support instead of emmc only (just figured not all devices uses emmc lol)
- [Scripts] Run all ext4 image operations through magicmask binary in flash scripts
- [Scripts] Updated scripts to use magicmask native commands to increase compatibility
- [Scripts] Add addon.d survival support
- [Scripts] Introduce `util_functions.sh`, used as a global shell script function source for all kinds of installation
- [MagicMaskBoot] Moved boot patch logic into magicmaskboot binary
- [MagicMaskSU] Does not fork new process for each request, add new threads instead
- [MagicMaskSU] Added multiuser support
- [MagicMaskSU] Introduce new timeout queue mechanism, prevent performance hit with poorly written su apps
- [MagicMaskSU] Multiple settings moved from prop detection to database
- [MagicMaskSU] Add namespace mode option support
- [MagicMaskSU] Add master-mount option
- [resetprop] Updated to latest AOSP upstream, support props from 5.0 to Android O
- [resetprop] Renamed all functions to prevent calling functions from external libc
- [magicmaskpolicy] Updated libsepol from official SELinux repo
- [magicmaskpolicy] Added xperm patching support (in order to make Android O work properly)
- [magicmaskpolicy] Updated rules for Android O, and Liveboot support
- [MagicMaskHide] Remove pseudo permissive mode, directly hide permissive status instead
- [MagicMaskHide] Remove unreliable list file monitor, change to daemon request mode
- [MagicMaskHide] MagicMaskHide is now enabled by default
- [MagicMaskHide] Update unmount policies, passes CTS in SafetyNet!
- [MagicMaskHide] Add more props for hiding
- [MagicMaskHide] Remove background magicmaskhide daemon, spawn short life process for unmounting purpose
- [Magic Mount] Ditched shell script based mounting, use proper C program to parse and mount files. Speed is SIGNIFICANTLY improved

### v12.0

- [General] Move most binaries into magicmask.img (Samsung cannot run su daemon in /data)
- [General] Move sepolicy live patch to `late_start` service
  This shall fix the long boot times, especially on Samsung devices
- [General] Add Samsung RKP hexpatch back, should now work on Samsung stock kernels
- [General] Fix installation with SuperSU
- [MagicMaskHide] Support other logcat `am_proc_start` patterns
- [MagicMaskHide] Change /sys/fs/selinux/enforce(policy) permissions if required
  Samsung devices cannot switch selinux states, if running on permissive custom kernel, the users will stuck at permissive
  If this scenario is detected, change permissions to hide the permissive state, leads to SafetyNet passes
- [MagicMaskHide] Add built in prop rules to fake KNOX status
  Samsung apps requiring KNOX status to be 0x0 should now work (Samsung Pay not tested)
- [MagicMaskHide] Remove all ro.build props, since they cause more issues than they benefit...
- [MagicMaskBoot] Add lz4 legacy format support (most linux kernel using lz4 for compression is using this)
- [MagicMaskBoot] Fix MTK kernels with MTK headers

### v11.5/11.6

- [Magic Mount] Fix mounting issues with devices that have separate /vendor partitions
- [MagicMaskBoot] Whole new boot image patching tool, please check release note for more info
- [magicmaskpolicy] Rename sepolicy-inject to magicmaskpolicy
- [magicmaskpolicy] Update a rule to allow chcon everything properly
- [MagicMaskHide] Prevent multirom crashes
- [MagicMaskHide] Add patches for ro.debuggable, ro.secure, ro.build.type, ro.build.tags, ro.build.selinux
- [MagicMaskHide] Change /sys/fs/selinux/enforce, /sys/fs/selinux/policy permissions for Samsung compatibility
- [MagicMaskSU] Fix read-only partition mounting issues
- [MagicMaskSU] Disable -cn option, the option will do nothing, preserved for compatibility

### v11.1

- [sepolicy-inject] Add missing messages
- [magicmaskhide] Start MagicMaskHide with scripts

### v11.0

- [Magic Mount] Support replacing symlinks.
  Symlinks cannot be a target of a bind mounted, so they are treated the same as new files
- [Magic Mount] Fix the issue when file/folder name contains spaces
- [BusyBox] Updated to v1.26.2. Should fix the black screen issues of FlashFire
- [resetprop] Support reading prop files that contains spaces in prop values
- [MagicMaskSU] Adapt communication to MagicMask Manager; stripped out unused data transfer
- [MagicMaskSU] Implement SuperUser access option (Disable, APP only, ADB Only, APP & ADB)
  phh Superuser app has this option but the feature isn't implemented within the su binary
- [MagicMaskSU] Fixed all issues with su -c "commands" (run commands with root)
  This feature is supposed to only allow one single option, but apparently adb shell su -c "command" doesn't work this way, and plenty of root apps don't follow the rule. The su binary will now consider everything after -c as a part of the command.
- [MagicMaskSU] Removed legacy context hack for TiBack, what it currently does is slowing down the invocation
- [MagicMaskSU] Preserve the current working directory after invoking su
  Previously phh superuser will change the path to /data/data after obtaining root shell. It will now stay in the same directory where you called su
- [MagicMaskSU] Daemon now also runs in u:r:su:s0 context
- [MagicMaskSU] Removed an unnecessary fork, reduce running processes and speed up the invocation
- [MagicMaskSU] Add -cn option to the binary
  Not sure if this is still relevant, and also not sure if implemented correctly, but hey it's here
- [sepolicy-inject] Complete re-write the command-line options, now nearly matches supolicy syntax
- [sepolicy-inject] Support all matching mode for nearly every action (makes pseudo enforced possible)
- [sepolicy-inject] Fixed an ancient bug that allocated memory isn't reset
- [uninstaller] Now works as a independent script that can be executed at boot
  Fully support recovery with no /data access, MagicMask uninstallation with MagicMask Manager
- [Addition] Busybox, MagicMaskHide, hosts settings can now be applied instantly; no reboots required
- [Addition] Add post-fs-data.d and service.d
- [Addition] Add option to disable MagicMask (MagicMaskSU will still be started)

### v10.2

- [Magic Mount] Remove apps/priv-app from whitelist, should fix all crashes
- [phh] Fix binary out-of-date issue
- [scripts] Fix root disappear issue when upgrading within MagicMask Manager

### v10

- [Magic Mount] Use a new way to mount system (vendor) mirrors
- [Magic Mount] Use universal way to deal with /vendor, handle both separate partition or not
- [Magic Mount] Adding **anything to any place** is now officially supported (including /system root and /vendor root)
- [Magic Mount] Use symlinks for mirroring back if possible, reduce bind mounts for adding files
- [MagicMask Hide] Check init namespace, zygote namespace to prevent Magic Mount breakage (a.k.a root loss)
- [MagicMask Hide] Send SIGSTOP to pause target process ASAP to prevent crashing if unmounting too late
- [MagicMask Hide] Hiding should work under any conditions, including adding libs and /system root etc.
- [phh] Root the device if no proper root detected
- [phh] Move `/sbin` to `/sbin_orig` and link back, fix Samsung no-suid issue
- [scripts] Improve SuperSU integration, now uses sukernel to patch ramdisk, support SuperSU built in ramdisk restore
- [template] Add PROPFILE option to load system.prop

### v9

- **[API Change] Remove the interface for post-fs modules**
- [resetprop] New tool "resetprop" is added to MagicMask to replace most post-fs modules' functionality
- [resetprop] MagicMask will now patch "ro.boot.verifiedbootstate", "ro.boot.flash.locked", "ro.boot.veritymode" to bypass Safety Net
- [Magic Mount] Move dummy skeleton / mirror / mountinfo filesystem tree to tmpfs
- [Magic Mount] Rewritten dummy cloning mechanism from scratch, will result in minimal bind mounts, minimal file traversal, eliminate all possible issues that might happen in extreme cases
- [Magic Mount] Adding new items to /system/bin, /system/vendor, /system/lib(64) is properly supported (devices with separate vendor partition is not supported yet)
- [MagicMask Hide] Rewritten from scratch, now run in daemon mode, proper list monitoring, proper mount detection, and maybe more.....
- [Boot Image] Add support for Motorola boot image dtb, it shall now unpack correctly
- [Uninstaller] Add removal of SuperSU custom patch script

### v8

- Add MagicMask Hide to bypass SafetyNet
- Improve SuperSU integration: no longer changes the SuperSU PATH
- Support rc script entry points not located in init.rc

### v7

- Fully open source
- Remove supolicy dependency, use my own sepolicy-injection
- Run everything in its own selinux domain, should fix all selinux issues
- Add Note 7 stock kernel hex patches
- Add support to install MagicMask in MagicMask Manager
- Add support for image merging for module flashing in MagicMask Manager
- Add root helpers for SuperSU auto module-ize and auto upgrading legacy phh superuser
- New paths to toggle busybox, and support all root solutions
- Remove root management API; both SuperSU and phh has their own superior solutions

### v6

- Fixed the algorithm for adding new files and dummy system
- Updated the module template with a default permission, since people tend to forget them :)

### v5

- Hotfix for older Android versions (detect policy before patching)
- Update uninstaller to NOT uninstall MagicMask Manager, since it cause problems

### v4

- Important: Uninstall v1 - v3 MagicMask before upgrading with the uninstaller in the OP!!
- Massive Rewrite MagicMask Interface API! All previous mods are NOT compatible! Please download the latest version of the mods you use (root/xposed)
- Mods are now installed independently in their own subfolder. This paves the way for future MagicMask Manager versions to manage mods, **just like how Xposed Modules are handled**
- Support small boot partition devices (Huawei devices)
- Use minimal sepolicy patch in boot image for smaller ramdisk size. Live patch policies after bootup
- Include updated open source sepolicy injection tool (source code available), support nearly all SuperSU supolicy tool's functionality

### v3

- Fix bootimg-extract for Exynos Samsung devices (thanks to @phhusson), should fix all Samsung device issues
- Add supolicy back to patch sepolicy (stock Samsung do not accept permissive domain)
- Update sepolicy-injection to patch su domain for Samsung devices to use phh's root
- Update root disable method, using more aggressive approach
- Use lazy unmount to unmount root from system, should fix some issues with custom roms
- Use the highest possible compression rate for ramdisk, hope to fix some devices with no boot partition space
- Detect boot partition space insufficient, will abort installer instead of breaking your device

### v2

- Fix verity patch. It should now work on all devices (might fix some of the unable-to-boot issues)
- All scripts will now run in selinux permissive mode for maximum compatibility (this will **NOT** turn your device to permissive)
- Add Nougat Developer Preview 5 support
- Add systemless host support for AdBlock Apps (enabled by default)
- Add support for new root disable method
- Remove sepolicy patches that uses SuperSU's supolicy tool; it is now using a minimal set of modifications
- Removed MagicMask Manager in MagicMask patch, it is now included in MagicMask phh's superuser only

### v1

- Initial release
