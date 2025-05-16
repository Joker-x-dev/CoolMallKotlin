plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.result"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 引入 model 模块
    implementation(project(":core:model"))
    // 引入 util 模块
    implementation(project(":core:util"))
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}