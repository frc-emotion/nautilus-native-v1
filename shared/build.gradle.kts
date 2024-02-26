plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.sqldelight)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget() {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(libs.ktor.client.core)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.runtime)
                implementation(libs.primitive.adapters)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                //put your android dependencies here
                implementation(libs.ktor.client.okhttp)
                implementation(libs.android.driver)
            }
        }
        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.native.driver)
            }
        }
    }
}

android {
    namespace = "org.nautilusapp.nautilus"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    buildToolsVersion = "34.0.0"
}
dependencies {
    implementation(libs.androidx.core.ktx)
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.nautilusapp.localstorage")
        }
    }
}