plugins {
    alias(libs.plugins.coolmall.android.library)
    alias(libs.plugins.coolmall.hilt)
}

android {
    defaultConfig {
        // QQ 登录相关配置
        manifestPlaceholders["TENCENT_APPID"] = "102756675"
    }
}

dependencies {
    // 引入 model 模块
    implementation(projects.core.model)
    // 引入 navigation 模块
    implementation(projects.navigation)
    // 引入 data 模块
    implementation(projects.core.data)
    // 引入 result 模块
    implementation(projects.core.result)
    // 导航
    implementation(libs.navigation.compose)
    // QQ SDK 依赖
    implementation(files("libs/open_sdk_lite.jar"))
}