# Frequently Asked Questions

### Q: I installed a module and it bootlooped my device. Help!

If you have USB debugging enabled in developer options, connect your phone to the PC. If your device is detected (check by `adb devices`), enter ADB shell and run the command `magicmask --remove-modules`. This will remove all your modules and automatically reboot the device.

If unfortunately you do not have USB debugging enabled, reboot into Safe Mode. Most modern Android devices support pressing a special key combo at boot to enter Safe Mode as an emergency option. MagicMask will detect Safe Mode being activated, and all modules will be disabled. Then reboot back to normal mode (the module disable state persists) and manage your modules through the MagicMask app.

### Q: Why is X app detecting root?

MagicMask no longer handles root hiding. There are plenty of MagicMask/Zygisk modules available that specifically provide these functionalities, please search around 😉

### Q: After I hidden the MagicMask app, the app icon is broken.

When hiding the MagicMask app, it will install a "stub" APK that has nothing in it. The only functionality this stub app has is downloading the full MagicMask app APK into its internal storage and dynamically loading it. Due to the fact that the APK is literally _empty_, it does not contain the image resource for the app icon.

When you open the hidden MagicMask app, it will offer you the option to create a shortcut in the homescreen (which has both the correct app name and icon) for your convenience. You can also manually ask the app to create the icon in app settings.
