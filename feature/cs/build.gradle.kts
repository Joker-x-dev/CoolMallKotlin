plugins {
    alias(libs.plugins.coolmall.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.feature.cs"
}
dependencies {
    // 网络请求
    implementation(libs.okhttp3)
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}