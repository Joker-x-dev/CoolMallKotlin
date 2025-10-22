package com.joker.coolmall

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * 配置基础的 Kotlin Android 选项
 *
 * 此函数统一配置 Android 项目的 Kotlin 编译选项，包括：
 * - 编译 SDK 版本设置
 * - Java 11 兼容性配置（通过 desugaring 支持）
 * - Kotlin JVM 目标版本配置
 * - Core library desugaring 启用
 *
 * @param commonExtension Android通用扩展
 * @author Joker.X
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().toString().toInt()
        }

        // 统一启用 BuildConfig 生成
        buildFeatures {
            buildConfig = true
        }

        compileOptions {
            // 通过 desugaring 支持 Java 11 API
            // https://developer.android.com/studio/write/java11-minimal-support-table
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }
    }

    configureKotlin<KotlinAndroidProjectExtension>()

    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("android.desugarJdkLibs").get())
    }
}

/**
 * 配置基础的 Kotlin JVM 选项（非 Android 项目）
 *
 * 此函数为纯 JVM 项目配置 Kotlin 编译选项
 *
 * @author Joker.X
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        // 通过 desugaring 支持 Java 11 API
        // https://developer.android.com/studio/write/java11-minimal-support-table
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    configureKotlin<KotlinJvmProjectExtension>()
}

/**
 * 配置基础的 Kotlin 编译选项
 *
 * 此函数配置通用的 Kotlin 编译器选项，包括：
 * - JVM 目标版本设置为 Java 11
 * - 警告处理配置
 * - 实验性 API 启用
 *
 * @author Joker.X
 */
private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() = configure<T> {
    // 将所有 Kotlin 警告视为错误（默认禁用）
    // 可通过在 ~/.gradle/gradle.properties 中设置 warningsAsErrors=true 来覆盖
    val warningsAsErrors = providers.gradleProperty("warningsAsErrors").map {
        it.toBoolean()
    }.orElse(false)

    when (this) {
        is KotlinAndroidProjectExtension -> compilerOptions
        is KotlinJvmProjectExtension -> compilerOptions
        else -> TODO("Unsupported project extension $this ${T::class}")
    }.apply {
        jvmTarget.set(JvmTarget.JVM_11)
        allWarningsAsErrors.set(warningsAsErrors)
        freeCompilerArgs.add(
            // 启用实验性协程 API，包括 Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
        freeCompilerArgs.add(
            /**
             * 在第 3 阶段后移除此参数。
             * https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-consistent-copy-visibility/#deprecation-timeline
             *
             * 弃用时间线
             * 第 3 阶段（预计 Kotlin 2.2 或 Kotlin 2.3）。
             * 默认行为改变。
             * 除非使用 ExposedCopyVisibility，否则生成的 'copy' 方法与主构造函数具有相同的可见性。
             * 二进制签名改变。不再报告声明错误。
             * '-Xconsistent-data-class-copy-visibility' 编译器标志和 ConsistentCopyVisibility 注解不再必要。
             */
            "-Xconsistent-data-class-copy-visibility"
        )
    }
}