package com.joker.coolmall.core.network.base

import retrofit2.Retrofit

/**
 * 网络数据源基类
 * 提供所有网络数据源实现的通用功能
 *
 * @author Joker.X
 */
abstract class BaseNetworkDataSource {
    /**
     * 创建API服务实例的辅助方法
     *
     * @param T API服务接口类型
     * @return API服务实例
     * @author Joker.X
     */
    protected inline fun <reified T> Retrofit.createService(): T {
        return this.create(T::class.java)
    }
} 