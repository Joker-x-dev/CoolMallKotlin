/**
 * 文件上传网络数据源实现类
 * 处理腾讯云文件上传的完整流程
 *
 * @author Joker.X
 */
package com.joker.coolmall.core.network.datasource.fileupload

import android.content.Context
import android.net.Uri
import android.util.Log
import com.joker.coolmall.core.model.entity.OssUpload
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.datasource.common.CommonNetworkDataSource
import com.joker.coolmall.core.network.service.FileUploadService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileUploadNetworkDataSourceImpl @Inject constructor(
    private val commonNetworkDataSource: CommonNetworkDataSource,
    private val fileUploadService: FileUploadService
) : FileUploadNetworkDataSource {

    companion object {
        private const val TAG = "FileUploadNetworkDataSource"
    }

    override suspend fun getUploadCredentials(): NetworkResponse<OssUpload> {
        return commonNetworkDataSource.uploadFile()
    }

    override fun uploadImage(imageUri: Uri): Flow<NetworkResponse<String>> = flow<NetworkResponse<String>> {
        try {
            Log.d(TAG, "开始上传单个图片文件")
            
            // 第一步：获取上传凭证
            val credentialsResponse = getUploadCredentials()
            
            if (credentialsResponse.isSucceeded && credentialsResponse.data != null) {
                val ossUpload = credentialsResponse.data
                val credentials = ossUpload?.credentials
                val uploadUrl = ossUpload?.url
                    
                if (credentials != null && uploadUrl != null) {
                    Log.d(TAG, "获取上传凭证成功，开始上传文件到腾讯云")
                    
                    // 第二步：上传文件到腾讯云
                    val uploadResult = fileUploadService.uploadImageToTencent(
                        imageUri = imageUri,
                        uploadUrl = uploadUrl,
                        tmpSecretId = credentials.tmpSecretId ?: "",
                        tmpSecretKey = credentials.tmpSecretKey ?: "",
                        sessionToken = credentials.sessionToken ?: ""
                    )
                    
                    emit(uploadResult)
                    
                    // 打印上传成功的URL
                    if (uploadResult.code == 1000 && uploadResult.data != null) {
                        Log.d(TAG, "单个图片上传成功，URL: ${uploadResult.data}")
                    }
                } else {
                    Log.e(TAG, "上传凭证无效")
                    emit(NetworkResponse(data = null, code = 400, message = "上传凭证无效"))
                }
            } else {
                Log.e(TAG, "获取上传凭证失败: ${credentialsResponse.message}")
                emit(NetworkResponse(data = null, code = credentialsResponse.code, message = credentialsResponse.message))
            }
        } catch (e: Exception) {
            Log.e(TAG, "上传过程中发生错误", e)
            emit(NetworkResponse(data = null, code = 500, message = "上传过程中发生错误: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override fun uploadImages(imageUris: List<Uri>): Flow<NetworkResponse<List<String>>> = flow<NetworkResponse<List<String>>> {
        try {
            Log.d(TAG, "开始批量上传 ${imageUris.size} 个图片文件")
            
            // 第一步：获取上传凭证
            val credentialsResponse = getUploadCredentials()
            
            if (credentialsResponse.isSucceeded && credentialsResponse.data != null) {
                val ossUpload = credentialsResponse.data
                val credentials = ossUpload?.credentials
                val uploadUrl = ossUpload?.url
                    
                if (credentials != null && uploadUrl != null) {
                    Log.d(TAG, "获取上传凭证成功，开始批量上传文件到腾讯云")
                    
                    // 第二步：批量上传文件到腾讯云
                    val uploadResult = fileUploadService.uploadImagesToTencent(
                        imageUris = imageUris,
                        uploadUrl = uploadUrl,
                        tmpSecretId = credentials.tmpSecretId ?: "",
                        tmpSecretKey = credentials.tmpSecretKey ?: "",
                        sessionToken = credentials.sessionToken ?: ""
                    )
                    
                    emit(uploadResult)
                    
                    // 打印上传成功的URL列表
                    if (uploadResult.code == 1000 && uploadResult.data != null) {
                        Log.d(TAG, "批量图片上传成功，URL列表: ${uploadResult.data}")
                    }
                } else {
                    Log.e(TAG, "上传凭证无效")
                    emit(NetworkResponse(data = null, code = 400, message = "上传凭证无效"))
                }
            } else {
                Log.e(TAG, "获取上传凭证失败: ${credentialsResponse.message}")
                emit(NetworkResponse(data = null, code = credentialsResponse.code, message = credentialsResponse.message))
            }
        } catch (e: Exception) {
            Log.e(TAG, "批量上传过程中发生错误", e)
            emit(NetworkResponse(data = null, code = 500, message = "批量上传过程中发生错误: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}