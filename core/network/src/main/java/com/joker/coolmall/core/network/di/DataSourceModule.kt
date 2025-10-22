package com.joker.coolmall.core.network.di

import com.joker.coolmall.core.network.datasource.address.AddressNetworkDataSource
import com.joker.coolmall.core.network.datasource.address.AddressNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.auth.AuthNetworkDataSource
import com.joker.coolmall.core.network.datasource.auth.AuthNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.banner.BannerNetworkDataSource
import com.joker.coolmall.core.network.datasource.banner.BannerNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.common.CommonNetworkDataSource
import com.joker.coolmall.core.network.datasource.common.CommonNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.coupon.CouponNetworkDataSource
import com.joker.coolmall.core.network.datasource.coupon.CouponNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.cs.CustomerServiceNetworkDataSource
import com.joker.coolmall.core.network.datasource.cs.CustomerServiceNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.feedback.FeedbackNetworkDataSource
import com.joker.coolmall.core.network.datasource.feedback.FeedbackNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.fileupload.FileUploadNetworkDataSource
import com.joker.coolmall.core.network.datasource.fileupload.FileUploadNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.goods.GoodsNetworkDataSource
import com.joker.coolmall.core.network.datasource.goods.GoodsNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.order.OrderNetworkDataSource
import com.joker.coolmall.core.network.datasource.order.OrderNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.page.PageNetworkDataSource
import com.joker.coolmall.core.network.datasource.page.PageNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.usercontributor.UserContributorNetworkDataSource
import com.joker.coolmall.core.network.datasource.usercontributor.UserContributorNetworkDataSourceImpl
import com.joker.coolmall.core.network.datasource.userinfo.UserInfoNetworkDataSource
import com.joker.coolmall.core.network.datasource.userinfo.UserInfoNetworkDataSourceImpl
import com.joker.coolmall.core.network.service.AddressService
import com.joker.coolmall.core.network.service.AuthService
import com.joker.coolmall.core.network.service.BannerService
import com.joker.coolmall.core.network.service.CommonService
import com.joker.coolmall.core.network.service.CouponService
import com.joker.coolmall.core.network.service.CustomerServiceService
import com.joker.coolmall.core.network.service.FeedbackService
import com.joker.coolmall.core.network.service.FileUploadService
import com.joker.coolmall.core.network.service.GoodsService
import com.joker.coolmall.core.network.service.OrderService
import com.joker.coolmall.core.network.service.PageService
import com.joker.coolmall.core.network.service.UserContributorService
import com.joker.coolmall.core.network.service.UserInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 数据源模块，提供所有网络数据源的依赖注入
 * 为Hilt提供各种网络数据源的实例
 *
 * @author Joker.X
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    /**
     * 提供页面相关网络数据源
     *
     * @param pageService 页面服务接口
     * @return 页面网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun providePageNetworkDataSource(pageService: PageService): PageNetworkDataSource {
        return PageNetworkDataSourceImpl(pageService)
    }

    /**
     * 提供认证相关网络数据源
     *
     * @param authService 认证服务接口
     * @return 认证网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideAuthNetworkDataSource(authService: AuthService): AuthNetworkDataSource {
        return AuthNetworkDataSourceImpl(authService)
    }

    /**
     * 提供用户信息相关网络数据源
     *
     * @param userInfoService 用户信息服务接口
     * @return 用户信息网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideUserInfoNetworkDataSource(userInfoService: UserInfoService): UserInfoNetworkDataSource {
        return UserInfoNetworkDataSourceImpl(userInfoService)
    }

    /**
     * 提供地址相关网络数据源
     *
     * @param addressService 地址服务接口
     * @return 地址网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideAddressNetworkDataSource(addressService: AddressService): AddressNetworkDataSource {
        return AddressNetworkDataSourceImpl(addressService)
    }

    /**
     * 提供订单相关网络数据源
     *
     * @param orderService 订单服务接口
     * @return 订单网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideOrderNetworkDataSource(orderService: OrderService): OrderNetworkDataSource {
        return OrderNetworkDataSourceImpl(orderService)
    }

    /**
     * 提供商品相关网络数据源
     *
     * @param goodsService 商品服务接口
     * @return 商品网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideGoodsNetworkDataSource(goodsService: GoodsService): GoodsNetworkDataSource {
        return GoodsNetworkDataSourceImpl(goodsService)
    }

    /**
     * 提供优惠券相关网络数据源
     *
     * @param couponService 优惠券服务接口
     * @return 优惠券网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideCouponNetworkDataSource(couponService: CouponService): CouponNetworkDataSource {
        return CouponNetworkDataSourceImpl(couponService)
    }

    /**
     * 提供轮播图相关网络数据源
     *
     * @param bannerService 轮播图服务接口
     * @return 轮播图网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideBannerNetworkDataSource(bannerService: BannerService): BannerNetworkDataSource {
        return BannerNetworkDataSourceImpl(bannerService)
    }

    /**
     * 提供客服相关网络数据源
     *
     * @param customerServiceService 客服服务接口
     * @return 客服网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideCustomerServiceNetworkDataSource(customerServiceService: CustomerServiceService): CustomerServiceNetworkDataSource {
        return CustomerServiceNetworkDataSourceImpl(customerServiceService)
    }

    /**
     * 提供通用基础网络数据源
     *
     * @param commonService 通用基础服务接口
     * @return 通用基础网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideCommonNetworkDataSource(commonService: CommonService): CommonNetworkDataSource {
        return CommonNetworkDataSourceImpl(commonService)
    }

    /**
     * 提供用户贡献者相关网络数据源
     *
     * @param userContributorService 用户贡献者服务接口
     * @return 用户贡献者网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideUserContributorNetworkDataSource(userContributorService: UserContributorService): UserContributorNetworkDataSource {
        return UserContributorNetworkDataSourceImpl(userContributorService)
    }

    /**
     * 提供文件上传网络数据源
     *
     * @param commonNetworkDataSource 通用网络数据源，用于获取上传配置
     * @param fileUploadService 文件上传服务接口
     * @return 文件上传网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideFileUploadNetworkDataSource(
        commonNetworkDataSource: CommonNetworkDataSource,
        fileUploadService: FileUploadService
    ): FileUploadNetworkDataSource {
        return FileUploadNetworkDataSourceImpl(
            commonNetworkDataSource,
            fileUploadService
        )
    }

    /**
     * 提供意见反馈网络数据源
     *
     * @param feedbackService 意见反馈服务接口
     * @return 意见反馈网络数据源实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideFeedbackNetworkDataSource(feedbackService: FeedbackService): FeedbackNetworkDataSource {
        return FeedbackNetworkDataSourceImpl(feedbackService)
    }
}