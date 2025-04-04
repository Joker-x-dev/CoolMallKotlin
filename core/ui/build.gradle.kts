plugins {
    id("com.joker.coolmall.android.library")
    id("com.joker.coolmall.android.compose")
}

android {
    namespace = "com.joker.coolmall.core.ui"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    // 图片加载框架
    implementation(libs.coil.compose)

    // lottie 动画
    // https://airbnb.io/lottie/#/android-compose
    implementation(libs.lottie.compose)
    
    // jsoup HTML解析库（用于HTML富文本渲染）
    implementation(libs.jsoup)
}