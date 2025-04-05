package com.joker.coolmall.core.data.repository

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
    fun updateAddress(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(addressNetworkDataSource.updateAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询地址
     */
    fun getAddressPage(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(addressNetworkDataSource.getAddressPage(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 查询地址列表
     */
    fun getAddressList(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(addressNetworkDataSource.getAddressList(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 删除地址
     */
    fun deleteAddress(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(addressNetworkDataSource.deleteAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 新增地址
     */
    fun addAddress(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(addressNetworkDataSource.addAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 地址信息
     */
    fun getAddressInfo(id: String): Flow<NetworkResponse<Any>> = flow {
        emit(addressNetworkDataSource.getAddressInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 默认地址
     */
    fun getDefaultAddress(): Flow<NetworkResponse<Any>> = flow {
        emit(addressNetworkDataSource.getDefaultAddress())
    }.flowOn(Dispatchers.IO)
} 