package com.joker.coolmall.navigation.routes

/**
 * 认证模块路由常量
 */
object AuthRoutes {
    /**
     * 认证模块根路由
     */
    private const val AUTH_ROUTE = "auth"

    /**
     * 登录主页路由
     */
    const val HOME = "$AUTH_ROUTE/home"

    /**
     * 账号密码登录路由
     */
    const val ACCOUNT_LOGIN = "$AUTH_ROUTE/account_login"

    /**
     * 短信验证码登录路由
     */
    const val SMS_LOGIN = "$AUTH_ROUTE/sms_login"

    /**
     * 注册页面路由
     */
    const val REGISTER = "$AUTH_ROUTE/register"

    /**
     * 找回密码路由
     */
    const val RESET_PASSWORD = "$AUTH_ROUTE/reset_password"
}