plugins {
    id("com.joker.coolmall.android.application")
    id("com.joker.coolmall.android.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    defaultConfig {
        ndk {
            abiFilters += listOf("arm64-v8a")
        }

        // 仅包括中文和英文必要的语言资源
        androidResources {
            localeFilters += listOf("zh", "en")
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:util"))
    implementation(project(":feature:main"))
    implementation(project(":feature:goods"))

    // region 依赖注入
    // https://developer.android.google.cn/training/dependency-injection/hilt-android?hl=zh-cn
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    //endregion

    compileOnly(libs.ksp.gradlePlugin)

    // 启动页
    implementation(libs.androidx.core.splashscreen)
    
    // LeakCanary - 内存泄漏检测工具（仅在debug构建中使用）
    // https://github.com/square/leakcanary
    debugImplementation(libs.leakcanary.android)
}