plugins {
    id("com.joker.coolmall.android.feature")
}

android {
    namespace = "com.joker.coolmall.feature.main"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 图片加载框架
    implementation(libs.coil.compose)
}