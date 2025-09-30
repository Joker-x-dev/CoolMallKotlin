plugins {
    alias(libs.plugins.coolmall.android.library)
    alias(libs.plugins.coolmall.hilt)
}

android {
    namespace = "com.joker.coolmall.core.data"
}

dependencies {
    // 引入 model 模块
    implementation(projects.core.model)
    // 引入网络模块
    implementation(projects.core.network)
    // 引入工具模块
    api(projects.core.util)
    // 引入数据存储模块
    implementation(projects.core.datastore)
    // 引入数据库模块
    implementation(projects.core.database)
    // 引入 result 模块
    implementation(projects.core.result)
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}