repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

plugins {
//    trick: for the same plugin versions in all sub-modules

//    id("com.android.application").version("8.1.1").apply(false)
//    id("com.android.library").version("8.1.1").apply(false)
//    kotlin("android").version("1.9.0").apply(false)
//    kotlin("multiplatform").version("1.9.0").apply(false)
//    kotlin("plugin.serialization").version("1.9.0").apply(false)

    id("com.android.application").version("8.1.1")
    id("com.android.library").version("8.1.1").apply(false)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin").version("2.0.1")
    kotlin("android").version("1.9.10")
    kotlin("multiplatform").version("1.9.10").apply(false)
    kotlin("plugin.serialization").version("1.9.10")
}

android {
    namespace = "org.team2658.emotion"
    compileSdkVersion = "android-34"
}

tasks.register("cleanBuild", Delete::class) {
    delete(rootProject.buildDir)
}
