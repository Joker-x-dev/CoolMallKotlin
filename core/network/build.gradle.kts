plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 引入 model 模块
    implementation(project(":core:model"))

    // 网络相关
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)
    implementation(libs.timber)

    // Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
}