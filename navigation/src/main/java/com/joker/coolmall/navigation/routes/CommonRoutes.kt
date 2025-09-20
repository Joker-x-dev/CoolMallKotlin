package com.joker.coolmall.navigation.routes

/**
 * 公共信息模块路由常量
 */
object CommonRoutes {
    /**
     * 公共信息模块根路由
     */
    private const val COMMON_ROUTE = "common"

    /**
     * 关于我们路由
     */
    const val ABOUT = "$COMMON_ROUTE/about"

    /**
     * WebView 页面路由
     */
    const val WEB = "$COMMON_ROUTE/web"

    /**
     * 设置页面路由
     */
    const val SETTINGS = "$COMMON_ROUTE/settings"

    /**
     * 用户协议路由
     */
    const val USER_AGREEMENT = "$COMMON_ROUTE/user_agreement"

    /**
     * 隐私政策路由
     */
    const val PRIVACY_POLICY = "$COMMON_ROUTE/privacy_policy"

    /**
     * 贡献者列表路由
     */
    const val CONTRIBUTORS = "$COMMON_ROUTE/contributors"
}