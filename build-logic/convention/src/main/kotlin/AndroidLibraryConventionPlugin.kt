import com.android.build.gradle.LibraryExtension
import com.joker.coolmall.configureKotlinAndroid
import com.joker.coolmall.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Android库模块构建插件
 *
 * 该插件用于配置Android库模块的基本构建设置，主要功能包括：
 * - 自动生成模块的命名空间
 * - 配置SDK版本和Java编译选项
 * - 管理通用依赖
 * - 配置ProGuard规则
 *
 * 插件会根据模块在项目中的位置自动生成合适的命名空间，支持以下模块类型：
 * - feature模块：com.joker.coolmall.feature.xxx
 * - core模块：com.joker.coolmall.core.xxx
 * - navigation模块：com.joker.coolmall.navigation
 * - 其他模块：根据模块路径生成
 *
 * @author Joker.X
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
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
                apply("com.android.library") // 应用Android库插件
                apply("org.jetbrains.kotlin.android") // 应用Kotlin Android插件
            }

            // 配置Android库扩展
            extensions.configure<LibraryExtension> {
                // 获取项目目录路径
                val projectDir = project.projectDir.path
                // 匹配feature模块路径
                val featureMatch = Regex(".*/(feature/[^/]+).*").find(projectDir)
                // 匹配core模块路径
                val coreMatch = Regex(".*/(core/[^/]+).*").find(projectDir)

                // 根据模块类型生成命名空间
                namespace = when {
                    // feature模块命名空间
                    featureMatch != null -> {
                        val featurePath = featureMatch.groupValues[1].replace("/", ".")
                        "${libs.findVersion("namespace").get()}.$featurePath"
                    }
                    // core模块命名空间
                    coreMatch != null -> {
                        val corePath = coreMatch.groupValues[1].replace("/", ".")
                        "${libs.findVersion("namespace").get()}.$corePath"
                    }
                    // navigation模块命名空间
                    project.path == ":navigation" -> {
                        "${libs.findVersion("namespace").get()}.navigation"
                    }
                    // 其他模块命名空间
                    else -> {
                        val modulePath = project.path.removePrefix(":").replace(":", ".")
                        "${libs.findVersion("namespace").get()}.$modulePath"
                    }
                }

                println("配置模块: ${project.path} 的命名空间为: $namespace")

                // 使用统一的 Kotlin Android 配置
                configureKotlinAndroid(this)

                defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()

                flavorDimensions += listOf("env")
                productFlavors {
                    create("dev") {
                        dimension = "env"
                        // 开发环境地址跟生产环境的地址暂时一样
                        buildConfigField("String", "BASE_URL", "\"https://mall.dusksnow.top/app/\"")
//                        buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8001/app/\"")
                        buildConfigField("Boolean", "DEBUG", "true")
                    }
                    create("prod") {
                        dimension = "env"
                        buildConfigField("String", "BASE_URL", "\"https://mall.dusksnow.top/app/\"")
                        buildConfigField("Boolean", "DEBUG", "false")
                    }
                }
            }

            configureDependencies()
        }
    }
}


/**
 * 配置库模块的通用依赖
 *
 * 添加Android开发所需的基础依赖，包括：
 * - AndroidX Core KTX
 * - AppCompat
 * - Material Design
 * - 测试相关依赖
 *
 * @author Joker.X
 */
internal fun Project.configureDependencies() {
    dependencies {
        "implementation"(libs.findLibrary("androidx.core.ktx").get())
        "implementation"(libs.findLibrary("androidx.appcompat").get())
        "implementation"(libs.findLibrary("material").get())
        "testImplementation"(libs.findLibrary("junit").get())
        "androidTestImplementation"(libs.findLibrary("androidx.junit").get())
        "androidTestImplementation"(libs.findLibrary("androidx.espresso.core").get())
    }
}