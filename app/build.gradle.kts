plugins {
    id("com.joker.coolmall.android.application")
    id("com.joker.coolmall.android.compose")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = libs.versions.namespace.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.namespace.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // 环境配置
    flavorDimensions += listOf("env")
    productFlavors {
        create("dev") {
            dimension = "env"
            // 开发环境配置
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:9900/dev/app/\"")
            buildConfigField("Boolean", "DEBUG", "true")
        }

        create("prod") {
            dimension = "env"
            // 生产环境配置
            buildConfigField("String", "BASE_URL", "\"https://api.coolmall.com/prod/app/\"")
            buildConfigField("Boolean", "DEBUG", "false")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
        // 启用BuildConfig生成
        buildConfig = true
    }
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:main"))
    implementation(project(":feature:goods"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // 导航
    // https://mvnrepository.com/artifact/androidx.navigation/navigation-compose
    implementation(libs.navigation.compose)

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

    compileOnly(libs.ksp.gradlePlugin)
}