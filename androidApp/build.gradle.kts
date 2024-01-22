plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
    kotlin("android")

}

android {
    namespace = "org.team2658.nautilus.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "org.team2658.scouting"
        minSdk = 26
        targetSdk = 34
        versionCode = 12
        versionName = "2.0.7"
    }
    signingConfigs {
        create("release") {
            keyAlias = "key"
            keyPassword = "Emotion2658"
            storeFile = file("./keystore.jks")
            storePassword = "Emotion2658"
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.compose.foundation:foundation:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.material3:material3:1.1.2")
//    implementation("androidx.compose.material3:pulltorefresh")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")

    val workVersion = "2.9.0"
    implementation("androidx.work:work-runtime-ktx:$workVersion")
}