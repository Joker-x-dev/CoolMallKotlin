package com.joker.coolmall.core.network.di

import javax.inject.Qualifier

/**
 * 文件上传专用的 OkHttpClient 限定符
 *
 * @author Joker.X
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FileUploadQualifier