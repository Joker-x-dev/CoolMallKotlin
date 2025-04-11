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
    implementation(project(":core:common"))

    // 图片加载框架
    implementation(libs.coil.compose)

    // lottie 动画
    // https://airbnb.io/lottie/#/android-compose
    implementation(libs.lottie.compose)
    
    // jsoup HTML解析库（用于HTML富文本渲染）
    implementation(libs.jsoup)

    // 上拉刷新下拉加载框架：https://github.com/scwang90/SmartRefreshLayout
    // 核心必须依赖
    implementation (libs.refresh.layout.kernel)
    // 经典刷新头
    implementation (libs.refresh.header.classics)
    // 经典加载
    implementation (libs.refresh.footer.classics)
}