package com.joker.coolmall.core.network.di

import com.joker.coolmall.core.network.service.GoodsService
import com.joker.coolmall.core.network.service.BannerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideGoodsService(retrofit: Retrofit): GoodsService {
        return retrofit.create(GoodsService::class.java)
    }

    @Provides
    @Singleton
    fun provideBannerService(retrofit: Retrofit): BannerService {
        return retrofit.create(BannerService::class.java)
    }
} 