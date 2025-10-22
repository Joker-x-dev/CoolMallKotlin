package com.joker.coolmall

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Project类的扩展属性，用于简化版本目录的访问
 *
 * 该扩展属性为Project类添加了一个名为libs的只读属性，用于访问项目的版本目录（Version Catalog）。
 * 版本目录是Gradle 7.0引入的新特性，用于集中管理项目依赖的版本信息。
 *
 * 使用方式：
 * ```kotlin
 * // 获取依赖版本
 * val version = libs.findVersion("someVersion").get()
 * // 获取依赖库
 * val library = libs.findLibrary("someLibrary").get()
 * ```
 *
 * @return VersionCatalog 返回名为"libs"的版本目录实例
 * @author Joker.X
 */
val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")