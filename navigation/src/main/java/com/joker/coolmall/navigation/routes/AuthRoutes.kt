package com.joker.coolmall.navigation.routes

import kotlinx.serialization.Serializable

/**
 * 认证模块路由
 *
 * @author Joker.X
 */
object AuthRoutes {
    /**
     * 登录主页路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Login

    /**
     * 账号密码登录路由
     *
     * @author Joker.X
     */
    @Serializable
    data object AccountLogin

    /**
     * 短信验证码登录路由
     *
     * @author Joker.X
     */
    @Serializable
    data object SmsLogin

    /**
     * 注册页面路由
     *
     * @author Joker.X
     */
    @Serializable
    data object Register

    /**
     * 找回密码路由
     *
     * @author Joker.X
     */
    @Serializable
    data object ResetPassword
}
