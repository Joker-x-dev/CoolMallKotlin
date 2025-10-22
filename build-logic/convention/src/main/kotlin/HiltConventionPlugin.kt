import com.joker.coolmall.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

/**
 * Hilt 依赖注入构建插件
 *
 * 该插件用于统一配置 Hilt 依赖注入相关的构建设置，主要功能包括：
 * - 应用 KSP 注解处理器
 * - 配置 Hilt 编译器依赖
 * - 支持 JVM 模块和 Android 模块
 * - 自动添加 Hilt Navigation Compose 和测试依赖
 *
 * 参考 Now in Android 项目的 HiltConventionPlugin 实现
 *
 * @author Joker.X
 */
class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.devtools.ksp")

            dependencies {
                "ksp"(libs.findLibrary("hilt.compiler").get())
            }

            // 支持 JVM 模块，基于 org.jetbrains.kotlin.jvm
            pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                dependencies {
                    "implementation"(libs.findLibrary("hilt.core").get())
                }
            }

            // 支持 Android 模块，基于 AndroidBasePlugin
            pluginManager.withPlugin("com.android.base") {
                apply(plugin = "dagger.hilt.android.plugin")
                dependencies {
                    "implementation"(libs.findLibrary("hilt.android").get())
                    // 添加 Hilt Navigation Compose 支持
                    "implementation"(libs.findLibrary("hilt.navigation.compose").get())
                    // 添加 Hilt 测试支持
                    "kspAndroidTest"(libs.findLibrary("hilt.compiler").get())
                    "androidTestImplementation"(libs.findLibrary("hilt.android.testing").get())
                }
            }
        }
    }
}