pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CoolMallKotlin"
include(":app")

// core 模块
include(":core:common")
include(":core:data")
//include(":core:designsystem")
include(":core:model")
include(":core:network")
//include(":core:ui")

// feature 模块
// 首页
include(":feature:mian")
// 用户认证
include(":feature:auth")
// 公共
include(":feature:common")
// 商品
include(":feature:goods")
// 启动流程
include(":feature:launch")
// 营销
include(":feature:market")
// 订单
include(":feature:order")
// 用户
include(":feature:user")
