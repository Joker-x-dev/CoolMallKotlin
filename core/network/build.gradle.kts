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
    implementation(project(":core:datastore"))

    // 网络相关
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)
    implementation(libs.timber)

    // Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    // 通过OkHttp的拦截器机制
    // 实现在应用通知栏显示网络请求功能
    // https://github.com/ChuckerTeam/chucker
    // debug 下的依赖
    debugImplementation(libs.chucker)
    // prod 下的空依赖
    releaseImplementation(libs.chucker.no.op)
}