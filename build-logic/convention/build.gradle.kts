// 应用Kotlin DSL插件，使构建脚本能够使用Kotlin语言编写
plugins {
    `kotlin-dsl`
}

// 定义构建逻辑模块的组名
group = "com.joker.coolmall.buildlogic"

// 配置Java编译选项
java {
    // 设置Java源代码和目标字节码的兼容性版本为Java 11
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

// 声明构建逻辑模块的依赖
dependencies {
    // 添加Android Gradle插件依赖（仅编译时需要）
    compileOnly(libs.android.gradlePlugin)
    // 添加Kotlin Gradle插件依赖（仅编译时需要）
    compileOnly(libs.kotlin.gradlePlugin)
    // 添加KSP注解处理器插件依赖（仅编译时需要）
    compileOnly(libs.ksp.gradlePlugin)
}

// 配置Gradle插件
gradlePlugin {
    plugins {
        // 注册Android应用程序插件
        register("androidApplication") {
            id = "com.joker.coolmall.android.application"
            implementationClass = "com.joker.coolmall.AndroidApplication"
        }
        // 注册Android库插件
        register("androidLibrary") {
            id = "com.joker.coolmall.android.library"
            implementationClass = "com.joker.coolmall.AndroidLibrary"
        }
        // 注册Android Compose插件
        register("androidCompose") {
            id = "com.joker.coolmall.android.compose"
            implementationClass = "com.joker.coolmall.AndroidCompose"
        }
        // 注册Android Feature模块插件
        register("androidFeature") {
            id = "com.joker.coolmall.android.feature"
            implementationClass = "com.joker.coolmall.AndroidFeature"
        }
        // 注册Android测试插件
        register("androidTest") {
            id = "com.joker.coolmall.android.test"
            implementationClass = "com.joker.coolmall.AndroidTest"
        }
    }
} 