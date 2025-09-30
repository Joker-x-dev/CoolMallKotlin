plugins {
    alias(libs.plugins.coolmall.android.feature)
}

android {
    namespace = "com.joker.coolmall.feature.main"
}
dependencies {
    // lottie 动画
    // https://airbnb.io/lottie/#/android-compose
    implementation(libs.lottie.compose)
}