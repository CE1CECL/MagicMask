plugins {
    id("MagicMaskPlugin")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
