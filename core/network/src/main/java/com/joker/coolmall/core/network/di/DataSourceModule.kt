package com.joker.coolmall.core.network.di

import com.joker.coolmall.core.network.datasource.banner.BannerNetworkDataSource
import com.joker.coolmall.core.network.datasource.banner.BannerNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.goods.GoodsNetworkDataSource
import com.joker.coolmall.core.network.datasource.goods.GoodsNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.page.PageNetworkDataSource
import com.joker.coolmall.core.network.datasource.page.PageNetworkDataSourceImpl
import com.joker.coolmall.core.network.service.BannerService
import com.joker.coolmall.core.network.service.GoodsService
import com.joker.coolmall.core.network.service.PageService
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideBannerNetworkDataSource(bannerService: BannerService): BannerNetworkDataSource {
        return BannerNetworkDataSourceImpl(bannerService)
    }

    @Provides
    @Singleton
    fun provideGoodsNetworkDataSource(goodsService: GoodsService): GoodsNetworkDataSource {
        return GoodsNetworkDataSourceImpl(goodsService)
    }

    @Provides
    @Singleton
    fun providePageNetworkDataSource(pageService: PageService): PageNetworkDataSource {
        return PageNetworkDataSourceImpl(pageService)
    }
}