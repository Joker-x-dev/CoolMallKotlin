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

    signingConfigs {
        // debug 模式下也使用正式签名
        getByName("debug") {
            // 哪个签名文件
            storeFile = file("joker_open_key.keystore")
            // 密钥别名
            keyAlias = "joker_open_key"
            // 密钥密码
            keyPassword = "joker123456"
            // 签名文件密码
            storePassword = "joker123456"
        }

        create("release") {
            // 哪个签名文件
            storeFile = file("joker_open_key.keystore")
            // 密钥别名
            keyAlias = "joker_open_key"
            // 密钥密码
            keyPassword = "joker123456"
            // 签名文件密码
            storePassword = "joker123456"
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
    // 首页模块
    implementation(project(":feature:main"))
    // 商品模块
    implementation(project(":feature:goods"))
    // 登录(认证)模块
    implementation(project(":feature:auth"))

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