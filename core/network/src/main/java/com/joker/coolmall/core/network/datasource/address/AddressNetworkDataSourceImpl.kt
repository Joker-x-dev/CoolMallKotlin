package com.joker.coolmall.core.network.datasource.address

import com.joker.coolmall.core.model.common.Id
import com.joker.coolmall.core.model.common.Ids
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.AddressService
import javax.inject.Inject

/**
 * 用户地址相关数据源实现类
 * 负责处理所有与用户地址相关的网络请求
 *
 * @param addressService 地址服务接口，用于发起实际的网络请求
 * @author Joker.X
 */
class AddressNetworkDataSourceImpl @Inject constructor(
    private val addressService: AddressService
) : BaseNetworkDataSource(), AddressNetworkDataSource {

    /**
     * 修改地址
     *
     * @param params 请求参数，包含地址ID和修改信息
     * @return 修改结果响应数据
     * @author Joker.X
     */
    override suspend fun updateAddress(params: Address): NetworkResponse<Unit> {
        return addressService.updateAddress(params)
    }

    /**
     * 分页查询地址
     *
     * @param params 请求参数，包含分页信息
     * @return 地址分页列表响应数据
     * @author Joker.X
     */
    override suspend fun getAddressPage(params: PageRequest): NetworkResponse<NetworkPageData<Address>> {
        return addressService.getAddressPage(params)
    }

    /**
     * 查询地址列表
     *
     * @return 地址列表响应数据
     * @author Joker.X
     */
    override suspend fun getAddressList(): NetworkResponse<List<Address>> {
        return addressService.getAddressList()
    }

    /**
     * 删除地址
     *
     * @param params 请求参数，包含地址ID数组
     * @return 删除结果响应数据
     * @author Joker.X
     */
    override suspend fun deleteAddress(params: Ids): NetworkResponse<Unit> {
        return addressService.deleteAddress(params)
    }

    /**
     * 新增地址
     *
     * @param params 请求参数，包含地址信息
     * @return 添加结果响应数据，包含新增地址的ID
     * @author Joker.X
     */
    override suspend fun addAddress(params: Address): NetworkResponse<Id> {
        return addressService.addAddress(params)
    }

    /**
     * 获取地址详情
     *
     * @param id 地址ID
     * @return 地址详情响应数据
     * @author Joker.X
     */
    override suspend fun getAddressInfo(id: Long): NetworkResponse<Address> {
        return addressService.getAddressInfo(id)
    }

    /**
     * 获取默认地址
     *
     * @return 默认地址响应数据
     * @author Joker.X
     */
    override suspend fun getDefaultAddress(): NetworkResponse<Address?> {
        return addressService.getDefaultAddress()
    }
} 