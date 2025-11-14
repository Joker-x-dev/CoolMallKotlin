plugins {
    alias(libs.plugins.coolmall.android.library)
    alias(libs.plugins.coolmall.hilt)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // 导航
    implementation(libs.navigation.compose)

    // Kotlin 序列化
    implementation(libs.kotlinx.serialization.json)

    // 数据模型（用于 NavigationResultKey 绑定具体实体，例如 Address）
    implementation(project(":core:model"))
}

android {
    namespace = "com.joker.coolmall.navigation"
}
