plugins {
    id("com.joker.coolmall.android.application")
    id("com.joker.coolmall.android.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    defaultConfig {
        // 仅包括中文和英文必要的语言资源
        androidResources {
            localeFilters += listOf("zh", "en")
        }
    }

    // ABI 分包配置 - 一次性打包多个架构版本
    splits {
        abi {
            // 启用 ABI 分包
            isEnable = true
            // 重置默认列表
            reset()
            // 包含的架构：32位和64位 ARM
            include("armeabi-v7a", "arm64-v8a")
            // 是否生成通用 APK（包含所有架构）
            // 设置为 true 会额外生成一个包含所有架构的 APK
            isUniversalApk = false
        }
    }

    signingConfigs {
        // 通用签名配置
        create("common") {
            // 哪个签名文件
            storeFile = file("joker_open_key.keystore")
            // 密钥别名
            keyAlias = "joker_open_key"
            // 密钥密码
            keyPassword = "joker123456"
            // 签名文件密码
            storePassword = "joker123456"

            // 启用所有签名方案以确保最大兼容性
            enableV1Signing = true  // JAR 签名 (Android 1.0+)
            enableV2Signing = true  // APK 签名 v2 (Android 7.0+)
            enableV3Signing = true  // APK 签名 v3 (Android 9.0+)
            enableV4Signing = true  // APK 签名 v4 (Android 11.0+)
        }
    }

    // 构建类型配置
    buildTypes {
        debug {
            // debug 模式下也使用正式签名配置 - 方便调试支付以及三方登录等功能
            signingConfig = signingConfigs.getByName("common")
        }

        release {
            signingConfig = signingConfigs.getByName("common")
            // 是否启用代码压缩
            isMinifyEnabled = true
            // 资源压缩
            isShrinkResources = true
            // 配置ProGuard规则文件
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(project(":core:data"))
    // 首页模块
    implementation(project(":feature:main"))
    // 商品模块
    implementation(project(":feature:goods"))
    // 登录(认证)模块
    implementation(project(":feature:auth"))
    // 用户模块
    implementation(project(":feature:user"))
    // 订单模块
    implementation(project(":feature:order"))
    // 客服模块
    implementation(project(":feature:cs"))
    // 通用模块
    implementation(project(":feature:common"))

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