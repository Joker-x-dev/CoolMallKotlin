package com.joker.coolmall.core.network.service

import com.joker.coolmall.core.model.entity.OssUpload
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.response.DictDataResponse
import com.joker.coolmall.core.model.response.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 通用基础接口
 *
 * @author Joker.X
 */
interface CommonService {

    /**
     * 获取文件上传配置
     *
     * @return OSS上传配置响应
     * @author Joker.X
     */
    @POST("base/comm/upload")
    suspend fun uploadFile(): NetworkResponse<OssUpload>

    /**
     * 获取系统参数配置
     *
     * @param key 参数键
     * @return 参数值响应
     * @author Joker.X
     */
    @GET("base/comm/param")
    suspend fun getParam(@Query("key") key: String): NetworkResponse<String>

    /**
     * 获取实体信息与路径
     *
     * @return 实体信息响应
     * @author Joker.X
     */
    @GET("base/comm/eps")
    suspend fun getEntityPathInfo(): NetworkResponse<Any>

    /**
     * 获取字典数据
     *
     * @param request 字典数据请求参数
     * @return 字典数据响应
     * @author Joker.X
     */
    @POST("dict/info/data")
    suspend fun getDictData(@Body request: DictDataRequest): NetworkResponse<DictDataResponse>
}