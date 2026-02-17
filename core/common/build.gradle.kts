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
    // Navigation3 Runtime（BaseViewModel 导航桥接需要 NavKey）
    implementation(libs.androidx.navigation3.runtime)

    // 引入 model 模块
    implementation(projects.core.model)
    // 引入 navigation 模块
    implementation(projects.core.navigation)
    // 引入 data 模块
    implementation(projects.core.data)
    // 引入 result 模块
    implementation(projects.core.result)
    // QQ SDK 依赖
    implementation(files("libs/open_sdk_lite.jar"))
}
