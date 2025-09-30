plugins {
    alias(libs.plugins.coolmall.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.joker.coolmall.core.util"
}

dependencies {
    // 引入 designsystem 模块
    implementation(projects.core.designsystem)
    implementation(projects.core.model)

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