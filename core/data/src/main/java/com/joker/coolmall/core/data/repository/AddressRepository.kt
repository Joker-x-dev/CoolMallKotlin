package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.entity.Address
import com.joker.coolmall.core.model.common.Id
import com.joker.coolmall.core.model.common.Ids
import com.joker.coolmall.core.model.request.PageRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.address.AddressNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 用户地址相关仓库
 */
class AddressRepository @Inject constructor(
    private val addressNetworkDataSource: AddressNetworkDataSource
) {
    /**
     * 修改地址
     */
    fun updateAddress(params: Address): Flow<NetworkResponse<Unit>> = flow {
        emit(addressNetworkDataSource.updateAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询地址
     */
    fun getAddressPage(params: PageRequest): Flow<NetworkResponse<NetworkPageData<Address>>> = flow {
        emit(addressNetworkDataSource.getAddressPage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 查询地址列表
     */
    fun getAddressList(): Flow<NetworkResponse<List<Address>>> = flow {
        emit(addressNetworkDataSource.getAddressList())
    }.flowOn(Dispatchers.IO)

    /**
     * 删除地址
     */
    fun deleteAddress(params: Ids): Flow<NetworkResponse<Unit>> = flow {
        emit(addressNetworkDataSource.deleteAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 新增地址
     */
    fun addAddress(params: Address): Flow<NetworkResponse<Id>> = flow {
        emit(addressNetworkDataSource.addAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 地址信息
     */
    fun getAddressInfo(id: Long): Flow<NetworkResponse<Address>> = flow {
        emit(addressNetworkDataSource.getAddressInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 默认地址
     */
    fun getDefaultAddress(): Flow<NetworkResponse<Address>> = flow {
        emit(addressNetworkDataSource.getDefaultAddress())
    }.flowOn(Dispatchers.IO)
} 