plugins {
    id("com.joker.coolmall.android.library")
}

// 如有特定依赖，可在此添加

android {
    namespace = "com.joker.coolmall.feature.common"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}