import com.android.build.api.dsl.ApplicationExtension
import com.joker.coolmall.configureKotlinAndroid
import com.joker.coolmall.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Android应用级构建插件
 *
 * 该插件用于配置Android应用模块的基本构建设置，包括：
 * - 应用ID和版本信息
 * - SDK版本配置
 * - Java编译选项
 * - 产品变体配置
 *
 * 主要通过扩展Android Gradle Plugin的ApplicationExtension来实现配置
 *
 * @author Joker.X
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
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
                apply("com.android.application") // 应用Android应用插件
                apply("org.jetbrains.kotlin.android") // 应用Kotlin Android插件
            }

            // 配置Android应用构建选项
            extensions.configure<ApplicationExtension> {
                // 使用统一的 Kotlin Android 配置
                configureKotlinAndroid(this)

                // 从版本目录获取并设置应用包名
                namespace = libs.findVersion("namespace").get().toString()

                // 默认配置
                defaultConfig {
                    // 设置应用ID（通常与包名相同）
                    applicationId = libs.findVersion("namespace").get().toString()
                    // 设置目标 SDK 版本
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                    // 设置应用版本号
                    versionCode = libs.findVersion("versionCode").get().toString().toInt()
                    // 设置应用版本名称
                    versionName = libs.findVersion("versionName").get().toString()

                    // 设置Android测试运行器
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                flavorDimensions += listOf("env")
                productFlavors {
                    create("dev") {
                        dimension = "env"
                    }
                    create("prod") {
                        dimension = "env"
                    }
                }
            }
        }
    }
}