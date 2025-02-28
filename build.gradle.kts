// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinKsp) apply false
    alias(libs.plugins.hiltAndroid) apply false

    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"

}