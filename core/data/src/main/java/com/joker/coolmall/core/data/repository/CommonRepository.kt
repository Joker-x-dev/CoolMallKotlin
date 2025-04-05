package com.joker.coolmall.core.data.repository

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
     * 文件上传
     */
    fun uploadFile(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(commonNetworkDataSource.uploadFile(params))
    }.flowOn(Dispatchers.IO)

    /**
     * 文件上传模式
     */
    fun getUploadMode(): Flow<NetworkResponse<Any>> = flow {
        emit(commonNetworkDataSource.getUploadMode())
    }.flowOn(Dispatchers.IO)

    /**
     * 参数配置
     */
    fun getParam(): Flow<NetworkResponse<Any>> = flow {
        emit(commonNetworkDataSource.getParam())
    }.flowOn(Dispatchers.IO)

    /**
     * 实体信息与路径
     */
    fun getEntityPathInfo(): Flow<NetworkResponse<Any>> = flow {
        emit(commonNetworkDataSource.getEntityPathInfo())
    }.flowOn(Dispatchers.IO)

    /**
     * 获取字典数据
     */
    fun getDictData(params: Any): Flow<NetworkResponse<Any>> = flow {
        emit(commonNetworkDataSource.getDictData(params))
    }.flowOn(Dispatchers.IO)
} 