plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    sourceSets.all {
        languageSettings {
            optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }
    }
}

dependencies {
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}