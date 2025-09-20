package com.joker.coolmall.core.network.datasource.common

import com.joker.coolmall.core.model.entity.OssUpload
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.response.DictDataResponse
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 通用基础数据源接口
 */
interface CommonNetworkDataSource {

    /**
     * 文件上传
     */
    suspend fun uploadFile(): NetworkResponse<OssUpload>

    /**
     * 参数配置
     */
    suspend fun getParam(key: String): NetworkResponse<String>

    /**
     * 实体信息与路径
     */
    suspend fun getEntityPathInfo(): NetworkResponse<Any>

    /**
     * 获取字典数据
     */
    suspend fun getDictData(request: DictDataRequest): NetworkResponse<DictDataResponse>
}