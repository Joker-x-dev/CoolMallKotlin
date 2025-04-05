package com.joker.coolmall.core.network.datasource.address

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 用户地址相关数据源接口
 */
interface AddressNetworkDataSource {

    /**
     * 修改地址
     */
    suspend fun updateAddress(params: Any): NetworkResponse<Any>

    /**
     * 分页查询地址
     */
    suspend fun getAddressPage(params: Any): NetworkResponse<Any>

    /**
     * 查询地址列表
     */
    suspend fun getAddressList(params: Any): NetworkResponse<Any>

    /**
     * 删除地址
     */
    suspend fun deleteAddress(params: Any): NetworkResponse<Any>

    /**
     * 新增地址
     */
    suspend fun addAddress(params: Any): NetworkResponse<Any>

    /**
     * 地址信息
     */
    suspend fun getAddressInfo(id: String): NetworkResponse<Any>

    /**
     * 默认地址
     */
    suspend fun getDefaultAddress(): NetworkResponse<Any>
} 