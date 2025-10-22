package com.joker.coolmall.core.model.entity

import kotlinx.serialization.Serializable

/**
 * 图片验证码模型
 * 用于登录、注册等需要验证码的场景
 *
 * @param data base64编码的图片验证码
 * @param captchaId 验证码ID
 * @author Joker.X
 */
@Serializable
data class Captcha(
    /**
     * base64 编码的图片验证码
     */
    val data: String = "",

    /**
     * 验证码 ID
     */
    val captchaId: String = ""
)