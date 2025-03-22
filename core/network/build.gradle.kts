plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    //依赖注入
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.joker.coolmall.core.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        // 启用BuildConfig生成
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // 引入 model 模块
    implementation(project(":core:model"))


    // kotlin序列化
    // https://kotlinlang.org/docs/serialization.html
    implementation(libs.kotlinx.serialization.json)

    // okhttp 网络框架
    // https://github.com/square/okhttp
    implementation(libs.okhttp)

    // 类型安全网络框架
    // https://github.com/square/retrofit
    implementation(libs.retrofit)

    // 让Retrofit支持Kotlinx Serialization
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // 网络请求框架日志框架
    implementation(libs.logging.interceptor)

    //日志框架
    //https://github.com/JakeWharton/timber
    implementation(libs.timber)


    //region 依赖注入
    //https://developer.android.google.cn/training/dependency-injection/hilt-android?hl=zh-cn
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    //endregion

}