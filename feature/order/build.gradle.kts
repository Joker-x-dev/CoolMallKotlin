plugins {
    id("com.joker.coolmall.android.feature")
}

android {
    namespace = "com.joker.coolmall.feature.order"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    //支付宝支付
    implementation(libs.alipaysdk.android)
}