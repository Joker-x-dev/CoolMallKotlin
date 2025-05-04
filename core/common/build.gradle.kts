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

    // 引入导航模块
    implementation(project(":navigation"))

    // 引入 result 模块
    implementation(project(":core:result"))

    // 导航
    implementation(libs.navigation.compose)

    // Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
}