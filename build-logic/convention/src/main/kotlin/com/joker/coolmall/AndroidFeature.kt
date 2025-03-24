package com.joker.coolmall

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeature : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.joker.coolmall.android.library")
                apply("com.joker.coolmall.android.compose")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
            }
            
            // 为 feature 模块启用 BuildConfig
            extensions.findByName("android")?.apply {
                this as com.android.build.gradle.LibraryExtension
                buildFeatures {
                    buildConfig = true
                }
            }

            dependencies {
                // 基础依赖
                "implementation"(project(":navigation"))
                "implementation"(project(":core:designsystem"))
                "implementation"(project(":core:ui"))
                
                // 导航相关
                "implementation"(libs.findLibrary("navigation.compose").get())

                // Hilt 依赖注入
                "implementation"(libs.findLibrary("hilt.android").get())
                "implementation"(libs.findLibrary("hilt.navigation.compose").get())
                "ksp"(libs.findLibrary("hilt.android.compiler").get())
                "kspAndroidTest"(libs.findLibrary("hilt.android.compiler").get())
                "androidTestImplementation"(libs.findLibrary("hilt.android.testing").get())
            }
        }
    }
} 