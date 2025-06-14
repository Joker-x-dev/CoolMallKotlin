plugins {
    id("com.joker.coolmall.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.core.util"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 引入 common 模块
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)

    // 吐司框架：https://github.com/getActivity/Toaster
    implementation(libs.toaster)

    // 权限框架：https://github.com/getActivity/XXPermissions
    implementation(libs.xxpermissions)

    // 腾讯存储 https://github.com/Tencent/MMKV
    implementation(libs.mmkv)

    //日志框架
    // https://github.com/JakeWharton/timber
    implementation(libs.timber)
}