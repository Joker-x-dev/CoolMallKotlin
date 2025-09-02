package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.OssUpload
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.response.DictDataResponse
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * 通用基础接口
 */
interface CommonService {

    /**
     * 文件上传
     */
    @POST("base/comm/upload")
    suspend fun uploadFile(): NetworkResponse<OssUpload>

    /**
     * 参数配置
     */
    @GET("base/comm/param")
    suspend fun getParam(): NetworkResponse<Any>

    /**
     * 实体信息与路径
     */
    @GET("base/comm/eps")
    suspend fun getEntityPathInfo(): NetworkResponse<Any>

    /**
     * 获取字典数据
     */
    @POST("dict/info/data")
    suspend fun getDictData(@Body request: DictDataRequest): NetworkResponse<DictDataResponse>
}