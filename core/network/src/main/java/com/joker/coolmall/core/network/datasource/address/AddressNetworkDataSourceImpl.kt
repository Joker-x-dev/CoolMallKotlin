package com.joker.coolmall.core.network.datasource.address

import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.AddressService
import javax.inject.Inject

/**
 * 用户地址相关数据源实现类
 * 负责处理所有与用户地址相关的网络请求
 * 
 * @property addressService 地址服务接口，用于发起实际的网络请求
 */
class AddressNetworkDataSourceImpl @Inject constructor(
    private val addressService: AddressService
) : BaseNetworkDataSource(), AddressNetworkDataSource {

    /**
     * 修改地址
     * 
     * @param params 请求参数，包含地址ID和修改信息
     * @return 修改结果响应数据
     */
    override suspend fun updateAddress(params: Any): NetworkResponse<Any> {
        return addressService.updateAddress(params)
    }

    /**
     * 分页查询地址
     * 
     * @param params 请求参数，包含分页信息
     * @return 地址分页列表响应数据
     */
    override suspend fun getAddressPage(params: Any): NetworkResponse<Any> {
        return addressService.getAddressPage(params)
    }

    /**
     * 查询地址列表
     * 
     * @param params 请求参数
     * @return 地址列表响应数据
     */
    override suspend fun getAddressList(params: Any): NetworkResponse<Any> {
        return addressService.getAddressList(params)
    }

    /**
     * 删除地址
     * 
     * @param params 请求参数，包含地址ID
     * @return 删除结果响应数据
     */
    override suspend fun deleteAddress(params: Any): NetworkResponse<Any> {
        return addressService.deleteAddress(params)
    }

    /**
     * 新增地址
     * 
     * @param params 请求参数，包含地址信息
     * @return 添加结果响应数据
     */
    override suspend fun addAddress(params: Any): NetworkResponse<Any> {
        return addressService.addAddress(params)
    }

    /**
     * 获取地址详情
     * 
     * @param id 地址ID
     * @return 地址详情响应数据
     */
    override suspend fun getAddressInfo(id: String): NetworkResponse<Any> {
        return addressService.getAddressInfo(id)
    }

    /**
     * 获取默认地址
     * 
     * @return 默认地址响应数据
     */
    override suspend fun getDefaultAddress(): NetworkResponse<Any> {
        return addressService.getDefaultAddress()
    }
} 