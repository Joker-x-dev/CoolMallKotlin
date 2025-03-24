package com.joker.coolmall

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.toString

class AndroidLibrary : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                val projectDir = project.projectDir.path
                val featureMatch = Regex(".*/(feature/[^/]+).*").find(projectDir)
                val coreMatch = Regex(".*/(core/[^/]+).*").find(projectDir)
                
                namespace = when {
                    featureMatch != null -> {
                        val featurePath = featureMatch.groupValues[1].replace("/", ".")
                        "${libs.findVersion("namespace").get()}.$featurePath"
                    }
                    coreMatch != null -> {
                        val corePath = coreMatch.groupValues[1].replace("/", ".")
                        "${libs.findVersion("namespace").get()}.$corePath"
                    }
                    project.path == ":navigation" -> {
                        "${libs.findVersion("namespace").get()}.navigation"
                    }
                    else -> {
                        val modulePath = project.path.removePrefix(":").replace(":", ".")
                        "${libs.findVersion("namespace").get()}.$modulePath"
                    }
                }
                
                println("配置模块: ${project.path} 的命名空间为: $namespace")
                
                configureAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
            }

            // 配置 Kotlin 编译选项
            tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_11.toString()
                }
            }

            configureDependencies()
        }
    }
}

internal fun Project.configureAndroid(
    commonExtension: LibraryExtension,
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().toString().toInt()

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
}

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