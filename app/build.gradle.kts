import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.sample.project.watchbox"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"${gradleLocalProperties(rootDir)["API_KEY"]}\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraint)
    implementation(Dependencies.androidxCoreKtx)
    implementation(Dependencies.androidxLifecycle)

    // Navigation
    implementation(Dependencies.androidxNavFragment)
    implementation(Dependencies.androidxNavUi)

    // Networking
    implementation(Dependencies.retrofit2)
    implementation(Dependencies.gson)
    implementation(Dependencies.httpLoggingInterceptor)

    // Multithreading
    implementation(Dependencies.rxJava3Android)
    implementation(Dependencies.rxJava3Kotlin)
    implementation(Dependencies.rxJava)
    implementation(Dependencies.rxJavaCallAdapter)

    // Room
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomRxJava3)
    kapt(Dependencies.roomCompiler)

    // DI, Hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)

    // Glide for images, gifs
    implementation(Dependencies.glide)
    annotationProcessor(Dependencies.glideCompiler)

    // Tests
    implementation(Dependencies.junit)
    testImplementation(Dependencies.junit)
    implementation(Dependencies.testExtJunit)
    testImplementation(Dependencies.mockitoCore)
    androidTestImplementation(Dependencies.espresso)
}