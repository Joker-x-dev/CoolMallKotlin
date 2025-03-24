package com.joker.coolmall

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibrary : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                // 自动处理命名空间
                val baseName = libs.findVersion("namespace").get().toString()
                val modulePath = project.path.removePrefix(":").replace(":", ".")
                namespace = if (modulePath.isNotEmpty()) {
                    "$baseName.$modulePath"
                } else {
                    baseName
                }
                
                println("配置模块: ${project.path} 的命名空间为: $namespace")
                
                // 配置公共 Android 设置
                compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

                defaultConfig {
                    minSdk = libs.findVersion("minSdk").get().toString().toInt()
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }

            // 配置 Kotlin 编译选项
            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_11.toString()
                }
            }

            // 添加公共依赖
            dependencies {
                "implementation"(libs.findLibrary("androidx.core.ktx").get())
                "implementation"(libs.findLibrary("androidx.appcompat").get())
                "implementation"(libs.findLibrary("material").get())
                "testImplementation"(libs.findLibrary("junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.espresso.core").get())
            }
        }
    }
} 