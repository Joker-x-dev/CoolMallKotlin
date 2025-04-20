package com.joker.coolmall.core.model

import android.util.Log
import kotlinx.serialization.Serializable

/**
 * 图片验证码模型
 * 用于登录、注册等需要验证码的场景
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
) {
    /**
     * 获取处理后的图片URL
     * 确保base64数据有正确的前缀
     */
    fun getProcessedImageData(): String {
        // 检查是否已经有完整的Data URL前缀
        if (data.startsWith("data:image")) {
            Log.d("Captcha", "图片数据已有前缀: ${data.take(30)}...")
            return data
        }
        
        // 如果没有前缀，添加标准的png base64前缀
        Log.d("Captcha", "图片数据添加前缀: data:image/png;base64,${data.take(30)}...")
        return "data:image/png;base64,$data"
    }
}