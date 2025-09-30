plugins {
    alias(libs.plugins.coolmall.android.library)
    alias(libs.plugins.coolmall.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.core.datastore"
}

dependencies {
    // 引入 util 模块
    implementation(projects.core.util)
    // 引入 model 模块
    implementation(projects.core.model)
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}