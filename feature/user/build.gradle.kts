plugins {
    id("com.joker.coolmall.android.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.feature.user"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}