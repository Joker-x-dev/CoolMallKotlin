package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.CommonRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import javax.inject.Inject

/**
 * 关于我们 ViewModel
 */
@HiltViewModel
class AboutViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {


    /**
     * 点击开发者信息
     * 打开开发者个人主页或联系方式
     */
    fun onDeveloperClick() {
        toWebPage("https://github.com/Joker-x-dev", "Joker.X")
    }

    /**
     * 点击贡献者列表
     * 查看项目贡献者列表
     */
    fun onContributorsClick() {
        // TODO: 导航到贡献者页面
    }

    // ==================== 项目地址相关点击事件 ====================

    /**
     * 点击GitHub项目地址
     */
    fun onGitHubClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin", "GitHub")
    }

    /**
     * 点击Gitee项目地址
     */
    fun onGiteeClick() {
        toWebPage("https://gitee.com/Joker-x-dev/CoolMallKotlin", "Gitee")
    }

    // ==================== 相关资源点击事件 ====================

    /**
     * 点击API文档
     */
    fun onApiDocClick() {
        toWebPage("https://coolmall.apifox.cn", "API 文档")
    }

    /**
     * 点击Demo下载
     */
    fun onDemoDownloadClick() {
        toWebPage("https://www.pgyer.com/CoolMallKotlinProdRelease", "Demo 下载")
    }

    /**
     * 点击图标来源
     */
    fun onIconSourceClick() {
        toWebPage("https://github.com/tuniaoTech", "图标来源")
    }

    // ==================== 讨论相关点击事件 ====================

    /**
     * 点击GitHub讨论区
     */
    fun onGitHubDiscussionClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin/discussions", "GitHub 讨论区")
    }

    /**
     * 点击QQ交流群
     */
    fun onQQGroupClick() {
    }

    /**
     * 点击微信交流群
     */
    fun onWeChatGroupClick() {
    }

    // ==================== 支持与帮助点击事件 ====================

    /**
     * 点击翻译帮助
     */
    fun onTranslationClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin/issues", "翻译")
    }

    /**
     * 点击支持项目
     */
    fun onSupportClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin", "支持")
    }

    /**
     * 点击帮助与反馈
     */
    fun onHelpClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin/issues", "帮助")
    }

    // ==================== 其他功能点击事件 ====================

    /**
     * 点击引用致谢
     * 显示项目中使用的第三方库和资源致谢
     */
    fun onCitationClick() {
        // TODO: 导航到引用致谢页面
    }

    /**
     * 点击用户协议
     * 显示用户使用协议
     */
    fun onUserAgreementClick() {
        // TODO: 导航到用户协议页面
    }

    /**
     * 点击隐私政策
     * 显示隐私政策内容
     */
    fun onPrivacyPolicyClick() {
        // TODO: 导航到隐私政策页面
    }

    /**
     * 点击开源许可
     */
    fun onOpenSourceLicenseClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin/blob/main/LICENSE", "开源许可")
    }

    /**
     * 跳转到网页页面
     * @param url 网页URL
     * @param title 页面标题
     */
    private fun toWebPage(url: String, title: String) {
        super.toPage(
            "${CommonRoutes.WEB}?url=${
                URLEncoder.encode(
                    url,
                    "UTF-8"
                )
            }&title=${URLEncoder.encode(title, "UTF-8")}"
        )
    }
}