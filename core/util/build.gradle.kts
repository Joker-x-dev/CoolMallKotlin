plugins {
    id("com.joker.coolmall.android.library")
}

android {
    namespace = "com.joker.coolmall.core.util"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 吐司框架：https://github.com/getActivity/Toaster
    api(libs.toaster)

    // 腾讯存储 https://github.com/Tencent/MMKV
    implementation (libs.mmkv)

    //日志框架
    // https://github.com/JakeWharton/timber
    implementation(libs.timber)
}