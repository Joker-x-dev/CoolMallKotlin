package com.joker.coolmall.core.common.manager

import android.app.Activity
import android.content.Context
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject

/**
 * QQ 登录管理器
 * 负责管理 QQ 登录状态和回调，实现模块间通信
 *
 * @author Joker.X
 */
class QQLoginManager private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: QQLoginManager? = null

        /**
         * 获取单例实例
         *
         * @return QQLoginManager实例
         * @author Joker.X
         */
        fun getInstance(): QQLoginManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: QQLoginManager().also { INSTANCE = it }
            }
        }
    }

    private var tencent: Tencent? = null

    // QQ 登录结果状态流
    private val _loginResult = MutableStateFlow<QQLoginResult?>(null)
    val loginResult: StateFlow<QQLoginResult?> = _loginResult.asStateFlow()

    /**
     * 初始化 QQ SDK
     *
     * @param context 应用上下文
     * @param appId QQ 应用 ID
     * @author Joker.X
     */
    fun init(context: Context, appId: String) {
        // 设置权限授权状态
        Tencent.setIsPermissionGranted(true)
        tencent = Tencent.createInstance(appId, context.applicationContext)
    }

    /**
     * 启动 QQ 登录
     *
     * @param activity 当前 Activity
     * @author Joker.X
     */
    fun startQQLogin(activity: Activity) {
        if (tencent == null) {
            _loginResult.value = QQLoginResult.Error("QQ SDK not initialized")
            return
        }
        tencent?.login(activity, "all", qqLoginListener)
    }

    /**
     * QQ 登录回调监听器
     * 设为公开属性，供 MainActivity 的 onActivityResult 使用
     */
    val qqLoginListener = object : IUiListener {
        override fun onComplete(response: Any?) {
            try {
                val jsonObject = response as JSONObject
                val accessToken = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN)
                val openId = jsonObject.getString(Constants.PARAM_OPEN_ID)

                // 发送登录成功结果
                _loginResult.value = QQLoginResult.Success(accessToken, openId)
            } catch (e: Exception) {
                _loginResult.value =
                    QQLoginResult.Error("Failed to parse login result: ${e.message}")
            }
        }

        override fun onError(error: UiError?) {
            _loginResult.value = QQLoginResult.Error("Login failed: ${error?.errorMessage}")
        }

        override fun onCancel() {
            _loginResult.value = QQLoginResult.Cancel
        }

        override fun onWarning(code: Int) {
            // QQ 登录警告处理
        }
    }

    /**
     * 清除登录结果状态
     */
    fun clearLoginResult() {
        _loginResult.value = null
    }
}

/**
 * QQ 登录结果密封类
 */
sealed class QQLoginResult {
    /**
     * 登录成功
     * @param accessToken 访问令牌
     * @param openId 用户唯一标识
     */
    data class Success(val accessToken: String, val openId: String) : QQLoginResult()

    /**
     * 登录失败
     * @param message 错误信息
     */
    data class Error(val message: String) : QQLoginResult()

    /**
     * 用户取消登录
     */
    object Cancel : QQLoginResult()
}