plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 引入 model 模块
    implementation(project(":core:model"))
    // 引入网络模块
    implementation(project(":core:network"))
    // 引入工具模块
    implementation(project(":core:util"))
    // 引入数据存储模块
    implementation(project(":core:datastore"))
    // 引入数据库模块
    implementation(project(":core:database"))

    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
}