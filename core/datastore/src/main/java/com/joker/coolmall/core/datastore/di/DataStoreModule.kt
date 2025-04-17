package com.joker.coolmall.core.datastore.di

import com.joker.coolmall.core.datastore.datasource.auth.AuthStoreDataSource
import com.joker.coolmall.core.datastore.datasource.auth.AuthStoreDataSourceImpl
import com.joker.coolmall.core.datastore.datasource.userinfo.UserInfoStoreDataSource
import com.joker.coolmall.core.datastore.datasource.userinfo.UserInfoStoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 数据存储模块
 * 提供用户认证和用户信息相关的数据源依赖
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    /**
     * 提供认证数据源接口实现
     */
    @Binds
    @Singleton
    abstract fun bindAuthStoreDataSource(
        impl: AuthStoreDataSourceImpl
    ): AuthStoreDataSource

    /**
     * 提供用户信息数据源接口实现
     */
    @Binds
    @Singleton
    abstract fun bindUserInfoStoreDataSource(
        impl: UserInfoStoreDataSourceImpl
    ): UserInfoStoreDataSource
} 