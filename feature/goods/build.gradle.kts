plugins {
    id("com.joker.coolmall.android.feature")
}

android {
    namespace = "com.joker.coolmall.feature.goods"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // 吐司框架：https://github.com/getActivity/Toaster
    implementation(libs.toaster)
}