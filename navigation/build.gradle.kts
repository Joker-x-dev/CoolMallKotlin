plugins {
    alias(libs.plugins.coolmall.android.library)
    alias(libs.plugins.coolmall.hilt)
}

dependencies {
    // 导航
    implementation(libs.navigation.compose)
}

android {
    namespace = "com.joker.coolmall.navigation"
}