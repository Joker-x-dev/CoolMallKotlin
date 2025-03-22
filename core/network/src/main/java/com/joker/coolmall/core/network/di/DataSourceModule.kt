package com.joker.coolmall.core.network.di

import com.joker.coolmall.core.network.datasource.goods.GoodsNetworkDataSource
import com.joker.coolmall.core.network.datasource.goods.GoodsNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    
    @Binds
    @Singleton
    abstract fun bindGoodsNetworkDataSource(
        impl: GoodsNetworkDataSourceImpl
    ): GoodsNetworkDataSource
} 