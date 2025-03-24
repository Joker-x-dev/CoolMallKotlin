plugins {
    id("com.joker.coolmall.android.feature")
}

android {
    // 明确指定命名空间，覆盖插件中的自动设置
    namespace = "com.joker.coolmall.feature.main"
}

dependencies {
    // 图片加载框架
    implementation(libs.coil.compose)
}