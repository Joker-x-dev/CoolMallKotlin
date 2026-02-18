package com.joker.coolmall.navigation.common

import com.joker.coolmall.navigation.navigate

/**
 * 公共信息模块导航封装
 *
 * @author Joker.X
 */
object CommonNavigator {

    /**
     * 跳转到关于我们页
     *
     * @author Joker.X
     */
    fun toAbout() {
        navigate(CommonRoutes.About)
    }

    /**
     * 跳转到 Web 页面
     *
     * @param url 页面地址
     * @param title 页面标题
     * @author Joker.X
     */
    fun toWeb(url: String, title: String? = null) {
        navigate(CommonRoutes.Web(url = url, title = title))
    }

    /**
     * 跳转到设置页
     *
     * @author Joker.X
     */
    fun toSettings() {
        navigate(CommonRoutes.Settings)
    }

    /**
     * 跳转到用户协议页
     *
     * @author Joker.X
     */
    fun toUserAgreement() {
        navigate(CommonRoutes.UserAgreement)
    }

    /**
     * 跳转到隐私政策页
     *
     * @author Joker.X
     */
    fun toPrivacyPolicy() {
        navigate(CommonRoutes.PrivacyPolicy)
    }

    /**
     * 跳转到贡献者列表页
     *
     * @author Joker.X
     */
    fun toContributors() {
        navigate(CommonRoutes.Contributors)
    }
}
