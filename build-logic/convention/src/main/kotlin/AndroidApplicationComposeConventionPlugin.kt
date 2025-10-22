import com.android.build.api.dsl.CommonExtension
import com.joker.coolmall.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Android 应用程序 Compose 插件
 *
 * 该插件专门用于配置 Android 应用程序的 Compose 相关设置
 * 继承自 AndroidApplication 插件的所有配置，并添加 Compose 特定配置
 *
 * @author Joker.X
 */
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // 应用 Android 应用程序插件
            pluginManager.apply("com.joker.coolmall.android.application")
            // 应用 Kotlin Compose 编译器插件
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            // 获取 Android 通用扩展并配置 Compose
            val extension = extensions.findByType(CommonExtension::class.java)
            extension?.let { configureAndroidCompose(it) }
        }
    }
}