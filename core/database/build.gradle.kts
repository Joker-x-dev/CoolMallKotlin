plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.joker.coolmall.core.database"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
}