plugins {
    id("com.joker.coolmall.android.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.feature.cs"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 网络请求
    implementation(libs.okhttp)
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}