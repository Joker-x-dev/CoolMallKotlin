import com.joker.coolmall.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Android Feature模块构建插件
 *
 * 该插件用于配置Android功能模块的构建设置，主要功能包括：
 * - 应用基础的Android库和Compose配置
 * - 启用BuildConfig生成
 * - 配置Feature模块通用依赖
 *
 * Feature模块是应用的功能模块，通常包含特定功能的UI和业务逻辑
 *
 * @author Joker.X
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    /**
     * 插件应用入口
     *
     * @param target 目标项目实例
     * @author Joker.X
     */
    override fun apply(target: Project) {
        with(target) {
            // 应用必要的Gradle插件
            pluginManager.apply {
                apply("com.joker.coolmall.android.library.compose") // 应用Android库和Compose配置
                apply("com.joker.coolmall.hilt") // 应用Hilt依赖注入
            }

            // 配置Android库扩展
            // buildConfig 配置已在 configureKotlinAndroid 中统一处理

            // 配置Feature模块依赖
            dependencies {
                // 项目内基础模块依赖
                "implementation"(project(":navigation")) // 导航模块
                "implementation"(project(":core:designsystem")) // 设计系统
                "implementation"(project(":core:ui")) // UI组件库
                "implementation"(project(":core:util")) // 工具类
                "implementation"(project(":core:data")) // 数据
                "implementation"(project(":core:common")) // 公共
                "implementation"(project(":core:model")) // 模型
                "implementation"(project(":core:result")) // 结果处理

                // Jetpack Navigation Compose导航框架
                "implementation"(libs.findLibrary("navigation.compose").get())

                // Hilt依赖注入相关
                "implementation"(libs.findLibrary("hilt.navigation.compose").get()) // Hilt导航集成
                "kspAndroidTest"(libs.findLibrary("hilt.compiler").get()) // 测试用Hilt编译器
                "androidTestImplementation"(
                    libs.findLibrary("hilt.android.testing").get()
                ) // Hilt测试支持
            }
        }
    }
}