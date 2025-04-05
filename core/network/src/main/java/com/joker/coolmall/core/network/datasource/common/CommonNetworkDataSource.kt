package com.joker.coolmall.core.network.datasource.common

import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 通用基础数据源接口
 */
interface CommonNetworkDataSource {

    /**
     * 文件上传
     */
    suspend fun uploadFile(params: Any): NetworkResponse<Any>

    /**
     * 文件上传模式
     */
    suspend fun getUploadMode(): NetworkResponse<Any>

    /**
     * 参数配置
     */
    suspend fun getParam(): NetworkResponse<Any>

    /**
     * 实体信息与路径
     */
    suspend fun getEntityPathInfo(): NetworkResponse<Any>

    /**
     * 获取字典数据
     */
    suspend fun getDictData(params: Any): NetworkResponse<Any>
} 