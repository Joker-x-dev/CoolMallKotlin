package com.joker.coolmall

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 配置 Android Compose 相关设置
 *
 * 该函数用于配置使用 Jetpack Compose UI 的模块，主要功能包括：
 * - 启用 Compose 构建功能
 * - 配置 Compose 编译器选项
 * - 添加 Compose 相关依赖
 *
 * 通过扩展 CommonExtension 来实现对 Android 库和应用模块的 Compose 配置
 *
 * @param commonExtension Android通用扩展
 * @author Joker.X
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        // 启用 Compose 构建功能
        buildFeatures {
            compose = true
        }
    }

    // 配置 Compose 相关依赖
    dependencies {
        // 使用 Compose BOM 统一依赖版本
        val bom = libs.findLibrary("androidx.compose.bom").get()
        "implementation"(platform(bom))

        // 核心 UI 组件
        "implementation"(libs.findLibrary("androidx.ui").get())
        "implementation"(libs.findLibrary("androidx.ui.graphics").get())
        "implementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
        "implementation"(libs.findLibrary("androidx.material3").get())

        // Compose 集成支持
        "implementation"(libs.findLibrary("androidx.activity.compose").get())
        "implementation"(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())

        // 开发调试工具
        "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
        "debugImplementation"(libs.findLibrary("androidx.ui.test.manifest").get())

        // 测试依赖
        "androidTestImplementation"(platform(bom))
        "androidTestImplementation"(libs.findLibrary("androidx.ui.test.junit4").get())
    }
}