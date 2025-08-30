package com.joker.coolmall.core.network.datasource.common

import com.joker.coolmall.core.model.request.DictDataRequest
import com.joker.coolmall.core.model.response.DictDataResponse
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.base.BaseNetworkDataSource
import com.joker.coolmall.core.network.service.CommonService
import javax.inject.Inject

/**
 * 通用基础数据源实现类
 * 负责处理所有基础通用功能的网络请求
 *
 * @property commonService 通用基础服务接口，用于发起实际的网络请求
 */
class CommonNetworkDataSourceImpl @Inject constructor(
    private val commonService: CommonService
) : BaseNetworkDataSource(), CommonNetworkDataSource {

    /**
     * 上传文件
     *
     * @param params 请求参数，包含文件数据等
     * @return 文件上传结果响应数据
     */
    override suspend fun uploadFile(params: Any): NetworkResponse<Any> {
        return commonService.uploadFile(params)
    }

    /**
     * 获取文件上传模式
     *
     * @return 文件上传模式响应数据
     */
    override suspend fun getUploadMode(): NetworkResponse<Any> {
        return commonService.getUploadMode()
    }

    /**
     * 获取系统参数配置
     *
     * @return 参数配置响应数据
     */
    override suspend fun getParam(): NetworkResponse<Any> {
        return commonService.getParam()
    }

    /**
     * 获取实体信息与路径
     *
     * @return 实体信息与路径响应数据
     */
    override suspend fun getEntityPathInfo(): NetworkResponse<Any> {
        return commonService.getEntityPathInfo()
    }

    /**
     * 获取字典数据
     *
     * @param request 请求参数，包含字典类型等
     * @return 字典数据响应数据
     */
    override suspend fun getDictData(request: DictDataRequest): NetworkResponse<DictDataResponse> {
        return commonService.getDictData(request)
    }
}