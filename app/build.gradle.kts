plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.project.nutriai"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.nutriai"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.kotpref.core)
    implementation(libs.kotpref.gsonSupport)
    implementation(libs.fancytoast)
    implementation(libs.gson)
    implementation(libs.coil.kt)
    implementation(libs.shimmer)
    implementation(libs.mpandroidchart)
    implementation(libs.markwon.core)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.generativeai)
    // CameraX
    implementation(libs.cameraCore)
    implementation(libs.camera2)
    implementation(libs.camera.view)
    implementation(libs.camera.lifecycle)

    implementation(libs.android.image.cropper)
}