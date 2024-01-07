plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.1.1").apply(false)
    id("com.android.library").version("8.1.1").apply(false)
    id("com.google.devtools.ksp").version("1.9.22-1.0.16").apply(false)
    id("app.cash.sqldelight").version("2.0.1").apply(false)
    kotlin("android").version("1.9.21").apply(false)
    kotlin("multiplatform").version("1.9.21").apply(false)
    kotlin("plugin.serialization").version("1.9.21").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
