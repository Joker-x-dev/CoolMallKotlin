package com.joker.coolmall.navigation.auth

import com.joker.coolmall.navigation.navigate

/**
 * 认证模块导航封装
 *
 * @author Joker.X
 */
object AuthNavigator {

    /**
     * 跳转到登录主页
     *
     * @author Joker.X
     */
    fun toLogin() {
        navigate(AuthRoutes.Login)
    }

    /**
     * 跳转到账号密码登录页
     *
     * @author Joker.X
     */
    fun toAccountLogin() {
        navigate(AuthRoutes.AccountLogin)
    }

    /**
     * 跳转到短信验证码登录页
     *
     * @author Joker.X
     */
    fun toSmsLogin() {
        navigate(AuthRoutes.SmsLogin)
    }

    /**
     * 跳转到注册页
     *
     * @author Joker.X
     */
    fun toRegister() {
        navigate(AuthRoutes.Register)
    }

    /**
     * 跳转到找回密码页
     *
     * @author Joker.X
     */
    fun toResetPassword() {
        navigate(AuthRoutes.ResetPassword)
    }
}
