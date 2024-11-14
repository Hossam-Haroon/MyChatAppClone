plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.dagger.hilt.android") // Apply Hilt plugin
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.8.10"// For Hilt annotation processing
}

android {
    namespace = "com.example.mychatappclone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mychatappclone"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    //glide compose
    //implementation ("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // backup4app
    implementation ("com.github.parse-community.Parse-SDK-Android:parse:1.26.0")
    //
    implementation ("com.github.skydoves:landscapist-glide:2.4.0")

    //pager dependency
    implementation ("com.google.accompanist:accompanist-pager:0.36.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.36.0")

    // chat sdk library
    implementation("io.getstream:stream-chat-android-compose:6.4.4")
    implementation("io.getstream:stream-chat-android-offline:6.4.4")
    implementation("androidx.compose.material:material-icons-extended:1.7.4")

    // Hilt main dependencies
    implementation ("com.google.dagger:hilt-android:2.52")


    kapt ("com.google.dagger:hilt-compiler:2.52")

// For Hilt integration with Jetpack Compose
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
    implementation ("com.google.code.gson:gson:2.10.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}