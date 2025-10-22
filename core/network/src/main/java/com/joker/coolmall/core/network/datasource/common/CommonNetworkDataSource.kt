package com.joker.coolmall.core.network.datasource.common

import com.joker.coolmall.core.model.entity.OssUpload
import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.response.DictDataResponse
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 通用基础数据源接口
 *
 * @author Joker.X
 */
interface CommonNetworkDataSource {

    /**
     * 获取文件上传配置
     *
     * @return OSS上传配置响应
     * @author Joker.X
     */
    suspend fun uploadFile(): NetworkResponse<OssUpload>

    /**
     * 获取系统参数配置
     *
     * @param key 参数键
     * @return 参数值响应
     * @author Joker.X
     */
    suspend fun getParam(key: String): NetworkResponse<String>

    /**
     * 获取实体信息与路径
     *
     * @return 实体信息响应
     * @author Joker.X
     */
    suspend fun getEntityPathInfo(): NetworkResponse<Any>

    /**
     * 获取字典数据
     *
     * @param request 字典数据请求参数
     * @return 字典数据响应
     * @author Joker.X
     */
    suspend fun getDictData(request: DictDataRequest): NetworkResponse<DictDataResponse>
}