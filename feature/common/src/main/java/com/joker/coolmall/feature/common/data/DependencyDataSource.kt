package com.joker.coolmall.feature.common.data

import com.joker.coolmall.feature.common.model.Dependency

/**
 * 依赖库数据源
 * 提供项目中使用的第三方依赖库信息
 *
 * @author Joker.X
 */
object DependencyDataSource {

    /**
     * 获取所有依赖库列表
     *
     * @return 依赖库列表
     * @author Joker.X
     */
    fun getAllDependencies(): List<Dependency> {
        return listOf(
            // Android 核心库
            Dependency(
                name = "AndroidX Core KTX",
                version = "1.17.0",
                description = "Android KTX 核心库，提供 Kotlin 扩展函数",
                websiteUrl = "https://developer.android.com/jetpack/androidx/releases/core",
                category = "Android 核心"
            ),
            Dependency(
                name = "AndroidX Lifecycle Runtime KTX",
                version = "2.9.4",
                description = "生命周期感知组件的 Kotlin 扩展",
                websiteUrl = "https://developer.android.com/jetpack/androidx/releases/lifecycle",
                category = "Android 核心"
            ),
            Dependency(
                name = "AndroidX Activity Compose",
                version = "1.11.0",
                description = "Activity 与 Compose 的集成库",
                websiteUrl = "https://developer.android.com/jetpack/androidx/releases/activity",
                category = "Android 核心"
            ),

            // Jetpack Compose
            Dependency(
                name = "Jetpack Compose BOM",
                version = "2025.09.00",
                description = "Compose 库版本管理，确保所有 Compose 库版本兼容",
                websiteUrl = "https://developer.android.com/jetpack/compose/bom",
                category = "UI 框架"
            ),
            Dependency(
                name = "Material3",
                version = "BOM 管理",
                description = "Material Design 3 组件库",
                websiteUrl = "https://developer.android.com/jetpack/compose/designsystems/material3",
                category = "UI 框架"
            ),

            // 导航
            Dependency(
                name = "Navigation Compose",
                version = "2.9.4",
                description = "Compose 导航组件",
                websiteUrl = "https://developer.android.com/jetpack/androidx/releases/navigation",
                category = "导航"
            ),

            // 网络请求
            Dependency(
                name = "OkHttp",
                version = "5.1.0",
                description = "高效的 HTTP 客户端库",
                websiteUrl = "https://square.github.io/okhttp/",
                category = "网络请求"
            ),
            Dependency(
                name = "Retrofit",
                version = "3.0.0",
                description = "类型安全的 HTTP 客户端",
                websiteUrl = "https://square.github.io/retrofit/",
                category = "网络请求"
            ),
            Dependency(
                name = "Chucker",
                version = "4.2.0",
                description = "网络请求调试工具",
                websiteUrl = "https://github.com/ChuckerTeam/chucker",
                category = "调试工具"
            ),

            // 序列化
            Dependency(
                name = "Kotlinx Serialization JSON",
                version = "1.9.0",
                description = "Kotlin 多平台序列化库",
                websiteUrl = "https://github.com/Kotlin/kotlinx.serialization",
                category = "序列化"
            ),

            // 依赖注入
            Dependency(
                name = "Hilt",
                version = "2.57.1",
                description = "基于 Dagger 的 Android 依赖注入库",
                websiteUrl = "https://developer.android.com/training/dependency-injection/hilt-android",
                category = "依赖注入"
            ),

            // 图片加载
            Dependency(
                name = "Coil Compose",
                version = "2.7.0",
                description = "Kotlin 协程图片加载库",
                websiteUrl = "https://coil-kt.github.io/coil/compose/",
                category = "图片加载"
            ),

            // 日志
            Dependency(
                name = "Timber",
                version = "5.0.1",
                description = "Android 日志工具库",
                websiteUrl = "https://github.com/JakeWharton/timber",
                category = "日志"
            ),

            // 数据存储
            Dependency(
                name = "MMKV",
                version = "2.2.3",
                description = "腾讯高性能键值存储框架",
                websiteUrl = "https://github.com/Tencent/MMKV",
                category = "数据存储"
            ),
            Dependency(
                name = "Room",
                version = "2.8.0",
                description = "SQLite 抽象层，提供流畅的数据库访问",
                websiteUrl = "https://developer.android.com/training/data-storage/room",
                category = "数据存储"
            ),

            // 动画
            Dependency(
                name = "Lottie Compose",
                version = "6.6.7",
                description = "After Effects 动画库",
                websiteUrl = "https://airbnb.io/lottie/#/android-compose",
                category = "动画"
            ),

            // 调试工具
            Dependency(
                name = "LeakCanary",
                version = "2.14",
                description = "内存泄漏检测工具",
                websiteUrl = "https://github.com/square/leakcanary",
                category = "调试工具"
            ),

            // Toast
            Dependency(
                name = "Toaster",
                version = "13.2",
                description = "Android 吐司框架",
                websiteUrl = "https://github.com/getActivity/Toaster",
                category = "UI 组件"
            ),

            // 权限
            Dependency(
                name = "XXPermissions",
                version = "26.5",
                description = "Android 权限请求框架",
                websiteUrl = "https://github.com/getActivity/XXPermissions",
                category = "权限管理"
            ),

            // 支付
            Dependency(
                name = "支付宝 SDK",
                version = "15.8.38",
                description = "支付宝移动支付 SDK",
                websiteUrl = "https://opendocs.alipay.com/open/54/104509",
                category = "支付"
            ),

            // 启动页
            Dependency(
                name = "AndroidX Core Splashscreen",
                version = "1.0.1",
                description = "启动画面 API，兼容 Android 12+",
                websiteUrl = "https://developer.android.com/jetpack/androidx/releases/core",
                category = "启动页"
            )
        )
    }

    /**
     * 根据分类获取依赖库列表
     *
     * @param category 分类名称
     * @return 指定分类的依赖库列表
     * @author Joker.X
     */
    fun getDependenciesByCategory(category: String): List<Dependency> {
        return getAllDependencies().filter { it.category == category }
    }

    /**
     * 获取所有分类
     *
     * @return 分类列表
     * @author Joker.X
     */
    fun getAllCategories(): List<String> {
        return getAllDependencies().map { it.category }.distinct().sorted()
    }
}