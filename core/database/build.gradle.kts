plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.core.database"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 引入 model 模块
    implementation(project(":core:model"))

    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    // Room 数据库支持
    implementation(libs.androidx.room.runtime)
    // 使用 KSP 插件
    ksp(libs.androidx.room.compiler)
    // Kotlin 协程支持
    implementation(libs.androidx.room.ktx)
    // 测试支持
    testImplementation(libs.androidx.room.testing)
    // Paging 3 集成支持
    implementation(libs.androidx.room.paging)
}