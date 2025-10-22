package com.joker.coolmall.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * 应用状态模块，提供全局应用状态相关的依赖
 * 为 AppState 提供所需的应用级协程作用域
 *
 * @author Joker.X
 */
@Module
@InstallIn(SingletonComponent::class)
object AppStateModule {

    /**
     * 提供应用级别的协程作用域
     * 使用SupervisorJob确保一个子协程的失败不会影响到其他协程
     *
     * @return 应用级别的协程作用域
     * @author Joker.X
     */
    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}

/**
 * 应用级别协程作用域限定符
 * 用于标识应用级别的长生命周期协程作用域
 *
 * @author Joker.X
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope 