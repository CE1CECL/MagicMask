plugins {
    id("com.android.library")
}

setupCommon()

android {
    namespace = "com.topjohnwu.magicmask.native"

    externalNativeBuild {
        ndkBuild {
            path("src/Android.mk")
        }
    }

    sourceSets.getByName("main") {
        manifest.srcFile("src/AndroidManifest.xml")
    }

    defaultConfig {
        externalNativeBuild {
            ndkBuild {
                // Pass arguments to ndk-build.
                arguments(
                    "B_MAGICMASK=1", "B_INIT=1", "B_BOOT=1", "B_TEST=1", "B_POLICY=1", "B_PRELOAD=1", "B_PROP=1"
                )
            }
        }
    }
}
