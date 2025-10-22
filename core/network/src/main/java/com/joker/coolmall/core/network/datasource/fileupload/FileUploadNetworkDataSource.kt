package com.joker.coolmall.core.network.datasource.fileupload

import android.net.Uri
import com.joker.coolmall.core.model.entity.OssUpload
import com.joker.coolmall.core.model.response.NetworkResponse
import kotlinx.coroutines.flow.Flow

/**
 * 文件上传网络数据源接口
 *
 * @author Joker.X
 */
interface FileUploadNetworkDataSource {

    /**
     * 获取文件上传凭证
     *
     * @return 上传凭证信息响应
     * @author Joker.X
     */
    suspend fun getUploadCredentials(): NetworkResponse<OssUpload>

    /**
     * 上传单个图片文件
     *
     * @param imageUri 图片URI
     * @return 上传结果流，包含文件URL
     * @author Joker.X
     */
    fun uploadImage(imageUri: Uri): Flow<NetworkResponse<String>>

    /**
     * 批量上传图片文件
     *
     * @param imageUris 图片URI列表
     * @return 上传结果流，包含文件URL列表
     * @author Joker.X
     */
    fun uploadImages(imageUris: List<Uri>): Flow<NetworkResponse<List<String>>>
}
