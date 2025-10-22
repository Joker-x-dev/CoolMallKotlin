package com.joker.coolmall.core.network.di

import android.content.Context
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
import com.joker.coolmall.core.network.service.impl.FileUploadServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 服务模块，提供所有网络服务接口的依赖注入
 * 为Hilt提供各种网络服务接口的实例
 *
 * @author Joker.X
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    /**
     * 提供页面相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 页面服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun providePageService(retrofit: Retrofit): PageService {
        return retrofit.create(PageService::class.java)
    }

    /**
     * 提供认证相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 认证服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    /**
     * 提供用户信息相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 用户信息服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideUserInfoService(retrofit: Retrofit): UserInfoService {
        return retrofit.create(UserInfoService::class.java)
    }

    /**
     * 提供地址相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 地址服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideAddressService(retrofit: Retrofit): AddressService {
        return retrofit.create(AddressService::class.java)
    }

    /**
     * 提供订单相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 订单服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideOrderService(retrofit: Retrofit): OrderService {
        return retrofit.create(OrderService::class.java)
    }

    /**
     * 提供商品相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 商品服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideGoodsService(retrofit: Retrofit): GoodsService {
        return retrofit.create(GoodsService::class.java)
    }

    /**
     * 提供优惠券相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 优惠券服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideCouponService(retrofit: Retrofit): CouponService {
        return retrofit.create(CouponService::class.java)
    }

    /**
     * 提供轮播图相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 轮播图服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideBannerService(retrofit: Retrofit): BannerService {
        return retrofit.create(BannerService::class.java)
    }

    /**
     * 提供客服相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 客服服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideCustomerServiceService(retrofit: Retrofit): CustomerServiceService {
        return retrofit.create(CustomerServiceService::class.java)
    }

    /**
     * 提供通用基础服务接口
     *
     * @param retrofit Retrofit实例
     * @return 通用基础服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideCommonService(retrofit: Retrofit): CommonService {
        return retrofit.create(CommonService::class.java)
    }

    /**
     * 提供文件上传服务接口
     *
     * @param okHttpClient OkHttpClient实例
     * @param context 应用上下文
     * @return 文件上传服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideFileUploadService(
        @FileUploadQualifier okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): FileUploadService {
        return FileUploadServiceImpl(
            okHttpClient = okHttpClient,
            context = context
        )
    }

    /**
     * 提供意见反馈相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 意见反馈服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideFeedbackService(retrofit: Retrofit): FeedbackService {
        return retrofit.create(FeedbackService::class.java)
    }

    /**
     * 提供用户贡献者相关服务接口
     *
     * @param retrofit Retrofit实例
     * @return 用户贡献者服务接口实现
     * @author Joker.X
     */
    @Provides
    @Singleton
    fun provideUserContributorService(retrofit: Retrofit): UserContributorService {
        return retrofit.create(UserContributorService::class.java)
    }
}