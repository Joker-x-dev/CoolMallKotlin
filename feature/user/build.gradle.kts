plugins {
    alias(libs.plugins.coolmall.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.feature.user"
}
dependencies {
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}