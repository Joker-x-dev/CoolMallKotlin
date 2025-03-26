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

    // 引入数据模块
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    //日志框架
    //https://github.com/JakeWharton/timber
    implementation(libs.timber)
}