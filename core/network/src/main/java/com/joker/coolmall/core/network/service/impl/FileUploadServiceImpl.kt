/**
 * 文件上传服务实现类
 * 处理腾讯云文件上传的具体网络操作
 *
 * @author Joker.X
 */
package com.joker.coolmall.core.network.service.impl

import android.content.Context
import android.net.Uri
import android.util.Log
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.network.di.FileUploadQualifier
import com.joker.coolmall.core.network.service.FileUploadService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileUploadServiceImpl @Inject constructor(
    @param:FileUploadQualifier private val okHttpClient: OkHttpClient,
    private val context: Context
) : FileUploadService {

    companion object {
        private const val TAG = "FileUploadService"
    }

    override suspend fun uploadImageToTencent(
        imageUri: Uri,
        uploadUrl: String,
        tmpSecretId: String,
        tmpSecretKey: String,
        sessionToken: String
    ): NetworkResponse<String> = withContext(Dispatchers.IO) {
        try {
            val fileName = generateFileName(imageUri)

            // 从URI读取文件内容
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val fileBytes = inputStream?.readBytes()
            inputStream?.close()

            if (fileBytes == null) {
                return@withContext NetworkResponse(
                    data = null,
                    code = 400,
                    message = "无法读取文件内容"
                )
            }

            // 获取文件的MIME类型
            val mimeType = context.contentResolver.getType(imageUri) ?: "image/jpeg"
            val fileRequestBody = fileBytes.toRequestBody(mimeType.toMediaType())

            // 构建multipart请求体
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "app/base/$fileName")
                .addFormDataPart("tmpSecretId", tmpSecretId)
                .addFormDataPart("tmpSecretKey", tmpSecretKey)
                .addFormDataPart("sessionToken", sessionToken)
                .addFormDataPart("file", fileName, fileRequestBody)
                .build()

            val request = Request.Builder()
                .url(uploadUrl)
                .post(requestBody)
                .build()

            val response = okHttpClient.newCall(request).execute()

            if (response.isSuccessful) {
                // 根据腾讯云COS规则生成文件访问URL
                // 格式: https://bucket-appid.cos.region.myqcloud.com/key
                val fileKey = "app/base/$fileName"
                val fileUrl = "$uploadUrl/$fileKey"
                Log.d(TAG, "文件上传成功，URL: $fileUrl")
                NetworkResponse(data = fileUrl, code = 1000, message = "上传成功")
            } else {
                Log.e(TAG, "文件上传失败，响应码: ${response.code}")
                NetworkResponse(
                    data = null,
                    code = response.code,
                    message = "上传失败: ${response.message}"
                )
            }
        } catch (e: IOException) {
            Log.e(TAG, "文件上传异常", e)
            NetworkResponse(data = null, code = 500, message = "上传异常: ${e.message}")
        } catch (e: Exception) {
            Log.e(TAG, "文件上传未知错误", e)
            NetworkResponse(data = null, code = 500, message = "上传错误: ${e.message}")
        }
    }

    override suspend fun uploadImagesToTencent(
        imageUris: List<Uri>,
        uploadUrl: String,
        tmpSecretId: String,
        tmpSecretKey: String,
        sessionToken: String
    ): NetworkResponse<List<String>> = withContext(Dispatchers.IO) {
        try {
            val uploadedUrls = mutableListOf<String>()

            for (uri in imageUris) {
                val singleResult = uploadImageToTencent(
                    imageUri = uri,
                    uploadUrl = uploadUrl,
                    tmpSecretId = tmpSecretId,
                    tmpSecretKey = tmpSecretKey,
                    sessionToken = sessionToken
                )

                if (singleResult.isSucceeded) {
                    singleResult.data?.let { uploadedUrls.add(it) }
                } else {
                    Log.e(TAG, "批量上传失败: ${singleResult.message}")
                    return@withContext NetworkResponse(
                        data = null,
                        code = singleResult.code,
                        message = "批量上传失败: ${singleResult.message}"
                    )
                }
            }

            Log.d(TAG, "批量上传成功，共上传 ${uploadedUrls.size} 个文件")
            NetworkResponse(data = uploadedUrls.toList(), code = 1000, message = "批量上传成功")
        } catch (e: Exception) {
            Log.e(TAG, "批量上传异常", e)
            NetworkResponse(data = null, code = 500, message = "批量上传异常: ${e.message}")
        }
    }

    /**
     * 生成文件名
     */
    private fun generateFileName(uri: Uri): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val extension = getFileExtension(uri)
        return "${uuid}_image.$extension"
    }

    /**
     * 获取文件扩展名
     */
    private fun getFileExtension(uri: Uri): String {
        return when {
            uri.toString().contains(".jpg", ignoreCase = true) -> "jpg"
            uri.toString().contains(".jpeg", ignoreCase = true) -> "jpeg"
            uri.toString().contains(".png", ignoreCase = true) -> "png"
            uri.toString().contains(".webp", ignoreCase = true) -> "webp"
            else -> "jpg" // 默认扩展名
        }
    }
}