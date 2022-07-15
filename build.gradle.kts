// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("com.google.dagger.hilt.android") version "2.41" apply false
}

buildscript {
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.jfrog.org/libs-snapshot") }
    }
    dependencies {
        classpath(Dependencies.androidxNavSafeArgs)
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}