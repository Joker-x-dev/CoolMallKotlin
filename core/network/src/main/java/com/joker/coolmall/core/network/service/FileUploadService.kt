package com.joker.coolmall.core.network.service

import android.net.Uri
import com.joker.coolmall.core.model.response.NetworkResponse

/**
 * 文件上传服务接口
 *
 * @author Joker.X
 */
interface FileUploadService {

    /**
     * 上传单个图片到腾讯云
     *
     * @param imageUri 图片URI
     * @param uploadUrl 上传URL
     * @param tmpSecretId 临时密钥ID
     * @param tmpSecretKey 临时密钥Key
     * @param sessionToken 会话令牌
     * @return 上传结果，包含文件访问URL
     * @author Joker.X
     */
    suspend fun uploadImageToTencent(
        imageUri: Uri,
        uploadUrl: String,
        tmpSecretId: String,
        tmpSecretKey: String,
        sessionToken: String
    ): NetworkResponse<String>

    /**
     * 批量上传图片到腾讯云
     *
     * @param imageUris 图片URI列表
     * @param uploadUrl 上传URL
     * @param tmpSecretId 临时密钥ID
     * @param tmpSecretKey 临时密钥Key
     * @param sessionToken 会话令牌
     * @return 上传结果，包含文件访问URL列表
     * @author Joker.X
     */
    suspend fun uploadImagesToTencent(
        imageUris: List<Uri>,
        uploadUrl: String,
        tmpSecretId: String,
        tmpSecretKey: String,
        sessionToken: String
    ): NetworkResponse<List<String>>
}
