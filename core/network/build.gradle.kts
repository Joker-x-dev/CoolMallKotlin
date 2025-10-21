plugins {
    alias(libs.plugins.coolmall.android.library)
    alias(libs.plugins.coolmall.hilt)
}

android {
    namespace = "com.joker.coolmall.core.network"

}
dependencies {
    // 引入 model 模块
    implementation(projects.core.model)
    implementation(projects.core.datastore)

    // 网络相关
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp3)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp.logging)
    implementation(libs.timber)

    // 通过OkHttp的拦截器机制
    // 实现在应用通知栏显示网络请求功能
    // https://github.com/ChuckerTeam/chucker
    // debug 下的依赖
    debugImplementation(libs.chucker)
    // prod 下的空依赖
    releaseImplementation(libs.chucker.no.op)
}