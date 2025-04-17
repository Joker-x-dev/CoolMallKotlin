plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.core.datastore"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 引入 util 模块
    implementation(project(":core:util"))
    // 引入 model 模块
    implementation(project(":core:model"))

    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
}