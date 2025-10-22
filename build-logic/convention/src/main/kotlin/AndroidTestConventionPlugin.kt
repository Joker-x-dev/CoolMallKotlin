import com.android.build.gradle.TestExtension
import com.joker.coolmall.configureKotlinAndroid
import com.joker.coolmall.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Android测试模块构建插件
 *
 * 该插件用于配置Android测试模块的基本构建设置，包括：
 * - SDK版本配置
 * - 测试运行器设置
 * - Java编译选项
 *
 * 主要通过扩展Android Gradle Plugin的TestExtension来实现配置
 *
 * @author Joker.X
 */
class AndroidTestConventionPlugin : Plugin<Project> {
    /**
     * 插件应用入口
     *
     * @param target 目标项目实例
     * @author Joker.X
     */
    override fun apply(target: Project) {
        with(target) {
            // 应用必要的Gradle插件
            with(pluginManager) {
                apply("com.android.test") // 应用Android测试插件
                apply("org.jetbrains.kotlin.android") // 应用Kotlin Android插件
            }

            // 配置Android测试扩展
            extensions.configure<TestExtension> {
                // 使用统一的 Kotlin Android 配置
                configureKotlinAndroid(this)

                // 默认配置
                defaultConfig {
                    // 设置目标SDK版本
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                    // 设置Android测试运行器
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
        }
    }
}