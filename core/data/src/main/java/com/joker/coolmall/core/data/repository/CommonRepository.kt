package com.joker.coolmall.core.data.repository

import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.response.DictDataResponse
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.common.CommonNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 通用基础仓库
 */
class CommonRepository @Inject constructor(
    private val commonNetworkDataSource: CommonNetworkDataSource
) {
    /**
     * 参数配置
     */
    fun getParam(key: String): Flow<NetworkResponse<String>> = flow {
        emit(commonNetworkDataSource.getParam(key))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取字典数据
     */
    fun getDictData(request: DictDataRequest): Flow<NetworkResponse<DictDataResponse>> = flow {
        emit(commonNetworkDataSource.getDictData(request))
    }.flowOn(Dispatchers.IO)
}