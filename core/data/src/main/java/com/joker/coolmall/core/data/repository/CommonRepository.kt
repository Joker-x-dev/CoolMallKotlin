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
 *
 * @param commonNetworkDataSource 通用网络数据源
 * @author Joker.X
 */
class CommonRepository @Inject constructor(
    private val commonNetworkDataSource: CommonNetworkDataSource
) {
    /**
     * 获取参数配置
     *
     * @param key 参数键名
     * @return 参数值Flow
     * @author Joker.X
     */
    fun getParam(key: String): Flow<NetworkResponse<String>> = flow {
        emit(commonNetworkDataSource.getParam(key))
    }.flowOn(Dispatchers.IO)

    /**
     * 获取字典数据
     *
     * @param request 字典数据请求参数
     * @return 字典数据Flow
     * @author Joker.X
     */
    fun getDictData(request: DictDataRequest): Flow<NetworkResponse<DictDataResponse>> = flow {
        emit(commonNetworkDataSource.getDictData(request))
    }.flowOn(Dispatchers.IO)
}
