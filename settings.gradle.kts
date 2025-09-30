// 启用类型安全的项目访问器功能
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Gradle插件管理配置
pluginManagement {
    // 包含build-logic目录作为构建逻辑模块
    includeBuild("build-logic")
    // 配置插件仓库
    repositories {
        // Google的Maven仓库，用于Android相关依赖
        google {
            content {
                // 通过正则表达式指定允许从Google仓库下载的包
                includeGroupByRegex("com\\.android.*") // Android相关包
                includeGroupByRegex("com\\.google.*") // Google相关包
                includeGroupByRegex("androidx.*") // AndroidX相关包
            }
        }
        mavenCentral() // Maven中央仓库
        gradlePluginPortal() // Gradle插件门户
    }
}

// 依赖解析管理配置
dependencyResolutionManagement {
    // 设置仓库模式为严格模式，禁止在项目中单独配置仓库
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    // 配置项目级依赖仓库
    repositories {
        google() // Google的Maven仓库
        mavenCentral() // Maven中央仓库
        // JitPack 远程仓库：https://jitpack.io
        maven { url = uri("https://jitpack.io") }
    }
}

// 设置根项目名称
rootProject.name = "CoolMallKotlin"

// 包含主应用模块
include(":app")

// 核心模块
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:designsystem")
include(":core:model")
include(":core:network")
include(":core:ui")
include(":core:util")
include(":core:result")

// 导航模块
include(":navigation")

// feature 功能模块
// 首页模块
include(":feature:main")
// 用户认证模块
include(":feature:auth")
// 公共功能模块
include(":feature:common")
// 商品模块
include(":feature:goods")
// 启动流程模块
include(":feature:launch")
// 营销模块
include(":feature:market")
// 订单模块
include(":feature:order")
// 用户模块
include(":feature:user")
// 客服模块
include(":feature:cs")
// 反馈模块
include(":feature:feedback")

// JDK 版本检查：确保使用 JDK 17 或更高版本进行构建
check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    CoolMall 项目需要 JDK 17+ 但当前使用的是 JDK ${JavaVersion.current()}。
    Java Home: [${System.getProperty("java.home")}]
    请参考: https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}
