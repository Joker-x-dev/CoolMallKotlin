package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.common.Id
import com.joker.coolmall.core.model.common.Ids
import com.joker.coolmall.core.model.entity.Address
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
 *
 * @param addressNetworkDataSource 地址网络数据源
 * @author Joker.X
 */
class AddressRepository @Inject constructor(
    private val addressNetworkDataSource: AddressNetworkDataSource
) {
    /**
     * 修改地址
     *
     * @param params 地址信息
     * @return 修改结果Flow
     * @author Joker.X
     */
    fun updateAddress(params: Address): Flow<NetworkResponse<Unit>> = flow {
        emit(addressNetworkDataSource.updateAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 分页查询地址
     *
     * @param params 分页请求参数
     * @return 地址分页数据Flow
     * @author Joker.X
     */
    fun getAddressPage(params: PageRequest): Flow<NetworkResponse<NetworkPageData<Address>>> =
        flow {
            emit(addressNetworkDataSource.getAddressPage(params))
        }.flowOn(Dispatchers.IO)

    /**
     * 查询地址列表
     *
     * @return 地址列表Flow
     * @author Joker.X
     */
    fun getAddressList(): Flow<NetworkResponse<List<Address>>> = flow {
        emit(addressNetworkDataSource.getAddressList())
    }.flowOn(Dispatchers.IO)

    /**
     * 删除地址
     *
     * @param params 地址ID列表
     * @return 删除结果Flow
     * @author Joker.X
     */
    fun deleteAddress(params: Ids): Flow<NetworkResponse<Unit>> = flow {
        emit(addressNetworkDataSource.deleteAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 新增地址
     *
     * @param params 地址信息
     * @return 新增地址ID的Flow
     * @author Joker.X
     */
    fun addAddress(params: Address): Flow<NetworkResponse<Id>> = flow {
        emit(addressNetworkDataSource.addAddress(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取地址信息
     *
     * @param id 地址ID
     * @return 地址信息Flow
     * @author Joker.X
     */
    fun getAddressInfo(id: Long): Flow<NetworkResponse<Address>> = flow {
        emit(addressNetworkDataSource.getAddressInfo(id))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取默认地址
     *
     * @return 默认地址Flow，可能为空
     * @author Joker.X
     */
    fun getDefaultAddress(): Flow<NetworkResponse<Address?>> = flow {
        emit(addressNetworkDataSource.getDefaultAddress())
    }.flowOn(Dispatchers.IO)
}
