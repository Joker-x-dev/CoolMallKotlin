plugins {
    alias(libs.plugins.coolmall.android.library)
    alias(libs.plugins.coolmall.hilt)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.core.database"
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    // 引入 model 模块
    implementation(projects.core.model)
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
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