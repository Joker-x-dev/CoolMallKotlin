package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 腾讯云上传凭证模型
 *
 * @param tmpSecretId 临时密钥ID
 * @param tmpSecretKey 临时密钥Key
 * @param sessionToken 会话令牌
 * @author Joker.X
 */
@Serializable
data class TencentCredentials(
    /**
     * 临时密钥ID
     */
    val tmpSecretId: String? = null,

    /**
     * 临时密钥Key
     */
    val tmpSecretKey: String? = null,

    /**
     * 会话令牌
     */
    val sessionToken: String? = null
)

/**
 * OSS上传响应模型（适配腾讯云）
 *
 * @param credentials 腾讯云凭证信息
 * @param requestId 请求ID
 * @param expiration 过期时间
 * @param startTime 开始时间戳
 * @param expiredTime 过期时间戳
 * @param url 上传URL
 * @author Joker.X
 */
@Serializable
data class OssUpload(
    /**
     * 腾讯云凭证信息
     */
    val credentials: TencentCredentials? = null,

    /**
     * 请求ID
     */
    val requestId: String? = null,

    /**
     * 过期时间
     */
    val expiration: String? = null,

    /**
     * 开始时间戳
     */
    val startTime: Long? = null,

    /**
     * 过期时间戳
     */
    val expiredTime: Long? = null,

    /**
     * 上传URL
     */
    val url: String? = null
)