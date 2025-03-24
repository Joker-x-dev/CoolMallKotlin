package com.joker.coolmall

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidCompose : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.findByType(CommonExtension::class.java)
            extension?.apply {
                buildFeatures {
                    compose = true
                }

                dependencies {
                    val bom = libs.findLibrary("androidx.compose.bom").get()
                    "implementation"(platform(bom))
                    "implementation"(libs.findLibrary("androidx.ui").get())
                    "implementation"(libs.findLibrary("androidx.ui.graphics").get())
                    "implementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
                    "implementation"(libs.findLibrary("androidx.material3").get())
                    "implementation"(libs.findLibrary("androidx.activity.compose").get())
                    "implementation"(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                    
                    "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
                    "debugImplementation"(libs.findLibrary("androidx.ui.test.manifest").get())
                    
                    "androidTestImplementation"(platform(bom))
                    "androidTestImplementation"(libs.findLibrary("androidx.ui.test.junit4").get())
                }
            }
        }
    }
} 