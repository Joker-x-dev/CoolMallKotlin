plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {

    defaultConfig {
        // QQ 登录相关配置
        manifestPlaceholders["TENCENT_APPID"] = "102756675"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 引入 model 模块
    implementation(project(":core:model"))

    // 引入导航模块
    implementation(project(":navigation"))

    // 引入 data 模块
    implementation(project(":core:data"))

    // 引入 result 模块
    implementation(project(":core:result"))

    // 导航
    implementation(libs.navigation.compose)

    // Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    // QQ SDK 依赖
    implementation(files("libs/open_sdk_lite.jar"))
}