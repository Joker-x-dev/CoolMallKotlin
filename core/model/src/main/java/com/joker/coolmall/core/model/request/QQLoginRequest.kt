package com.joker.coolmall.core.model.request

import kotlinx.serialization.Serializable

/**
 * QQ 登录/绑定请求模型
 */
@Serializable
data class QQLoginRequest(
    /**
     * 访问令牌
     */
    val accessToken: String,

    /**
     * 用户唯一标识
     */
    val openId: String,
)
