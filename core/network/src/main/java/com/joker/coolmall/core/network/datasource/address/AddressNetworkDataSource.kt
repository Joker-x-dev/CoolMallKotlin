package com.joker.coolmall.core.network.datasource.address

import com.joker.coolmall.core.model.common.Id
import com.joker.coolmall.core.model.common.Ids
import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 用户地址相关数据源接口
 */
interface AddressNetworkDataSource {

    /**
     * 修改地址
     */
    suspend fun updateAddress(params: Address): NetworkResponse<Unit>

    /**
     * 分页查询地址
     */
    suspend fun getAddressPage(params: PageRequest): NetworkResponse<NetworkPageData<Address>>

    /**
     * 查询地址列表
     */
    suspend fun getAddressList(): NetworkResponse<List<Address>>

    /**
     * 删除地址
     */
    suspend fun deleteAddress(params: Ids): NetworkResponse<Unit>

    /**
     * 新增地址
     */
    suspend fun addAddress(params: Address): NetworkResponse<Id>

    /**
     * 地址信息
     */
    suspend fun getAddressInfo(id: Long): NetworkResponse<Address>

    /**
     * 默认地址
     */
    suspend fun getDefaultAddress(): NetworkResponse<Address?>
} 