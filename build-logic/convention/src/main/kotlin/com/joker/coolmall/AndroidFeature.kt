package com.joker.coolmall

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
 */
class AndroidFeature : Plugin<Project> {
    /**
     * 插件应用入口
     * 
     * @param target 目标项目实例
     */
    override fun apply(target: Project) {
        with(target) {
            // 应用必要的Gradle插件
            pluginManager.apply {
                apply("com.joker.coolmall.android.library") // 应用Android库配置
                apply("com.joker.coolmall.android.compose") // 应用Compose UI配置
                apply("com.google.devtools.ksp") // 应用KSP注解处理器
                apply("com.google.dagger.hilt.android") // 应用Hilt依赖注入
            }
            
            // 配置Android库扩展
            extensions.findByName("android")?.apply {
                this as com.android.build.gradle.LibraryExtension
                // 启用BuildConfig生成，用于在代码中访问构建配置
                buildFeatures {
                    buildConfig = true
                }
            }

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
                "implementation"(libs.findLibrary("hilt.android").get()) // Hilt运行时
                "implementation"(libs.findLibrary("hilt.navigation.compose").get()) // Hilt导航集成
                "ksp"(libs.findLibrary("hilt.android.compiler").get()) // Hilt编译时注解处理
                "kspAndroidTest"(libs.findLibrary("hilt.android.compiler").get()) // 测试用Hilt编译器
                "androidTestImplementation"(libs.findLibrary("hilt.android.testing").get()) // Hilt测试支持
            }
        }
    }
}