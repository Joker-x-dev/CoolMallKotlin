// 导入Kotlin Gradle DSL中的JVM目标版本枚举
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// 应用Kotlin DSL插件，使构建脚本能够使用Kotlin语言编写
plugins {
    `kotlin-dsl`
}

// 配置Java编译选项
// 设置Java源代码和目标字节码的兼容性版本为Java 11
java {
    sourceCompatibility = JavaVersion.VERSION_11 // 源代码兼容性
    targetCompatibility = JavaVersion.VERSION_11 // 目标平台兼容性
}

// 配置Kotlin编译任务
// 为所有Kotlin编译任务设置JVM目标版本
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        // 设置Kotlin编译器的JVM目标版本为11
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

// 声明构建逻辑模块的依赖
dependencies {
    // 添加Android Gradle插件依赖
    implementation(libs.android.gradlePlugin)
    // 添加Kotlin Gradle插件依赖
    implementation(libs.kotlin.gradlePlugin)
}