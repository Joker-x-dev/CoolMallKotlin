package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.feature.common.data.DependencyDataSource
import com.joker.coolmall.feature.common.model.Dependency
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.CommonRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URLEncoder
import javax.inject.Inject

/**
 * 关于我们 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class AboutViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {

    // ==================== 依赖弹出层状态 ====================

    /**
     * 依赖弹出层显示状态
     */
    private val _showDependencyModal = MutableStateFlow(false)
    val showDependencyModal: StateFlow<Boolean> = _showDependencyModal.asStateFlow()

    /**
     * 依赖列表数据
     */
    val dependencies: List<Dependency> = DependencyDataSource.getAllDependencies()


    /**
     * 点击开发者信息
     * 打开开发者个人主页或联系方式
     *
     * @author Joker.X
     */
    fun onDeveloperClick() {
        toWebPage("https://github.com/Joker-x-dev", "Joker.X")
    }

    /**
     * 点击贡献者列表
     * 查看项目贡献者列表
     *
     * @author Joker.X
     */
    fun onContributorsClick() {
        navigate(CommonRoutes.Contributors)
    }

    // ==================== 项目地址相关点击事件 ====================

    /**
     * 点击GitHub项目地址
     *
     * @author Joker.X
     */
    fun onGitHubClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin", "GitHub")
    }

    /**
     * 点击Gitee项目地址
     *
     * @author Joker.X
     */
    fun onGiteeClick() {
        toWebPage("https://gitee.com/Joker-x-dev/CoolMallKotlin", "Gitee")
    }

    // ==================== 相关资源点击事件 ====================

    /**
     * 点击API文档
     *
     * @author Joker.X
     */
    fun onApiDocClick() {
        toWebPage("https://coolmall.apifox.cn", "API 文档")
    }

    /**
     * 点击Demo下载
     *
     * @author Joker.X
     */
    fun onDemoDownloadClick() {
        toWebPage("https://www.pgyer.com/CoolMallKotlinProdRelease", "Demo 下载")
    }

    /**
     * 点击图标来源
     *
     * @author Joker.X
     */
    fun onIconSourceClick() {
        toWebPage("https://github.com/tuniaoTech", "图标来源")
    }

    // ==================== 讨论相关点击事件 ====================

    /**
     * 点击GitHub讨论区
     *
     * @author Joker.X
     */
    fun onGitHubDiscussionClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin/discussions", "GitHub 讨论区")
    }

    /**
     * 点击QQ交流群
     *
     * @author Joker.X
     */
    fun onQQGroupClick() {
    }

    /**
     * 点击微信交流群
     *
     * @author Joker.X
     */
    fun onWeChatGroupClick() {
    }

    // ==================== 支持与帮助点击事件 ====================

    /**
     * 点击翻译帮助
     *
     * @author Joker.X
     */
    fun onTranslationClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin/issues", "翻译")
    }

    /**
     * 点击支持项目
     *
     * @author Joker.X
     */
    fun onSupportClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin", "支持")
    }

    /**
     * 点击帮助与反馈
     *
     * @author Joker.X
     */
    fun onHelpClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin/issues", "帮助")
    }

    // ==================== 其他功能点击事件 ====================

    /**
     * 点击引用致谢
     * 显示项目中使用的第三方库和资源致谢
     *
     * @author Joker.X
     */
    fun onCitationClick() {
        _showDependencyModal.value = true
    }

    /**
     * 关闭依赖弹出层
     *
     * @author Joker.X
     */
    fun onDismissDependencyModal() {
        _showDependencyModal.value = false
    }

    /**
     * 点击依赖项
     * 打开依赖的官方网站
     *
     * @param dependency 依赖对象
     * @author Joker.X
     */
    fun onDependencyClick(dependency: Dependency) {
        if (dependency.websiteUrl.isNotEmpty()) {
            toWebPage(dependency.websiteUrl, dependency.name)
        }
        // 点击后关闭弹出层
        _showDependencyModal.value = false
    }

    /**
     * 点击用户协议
     * 显示用户使用协议
     *
     * @author Joker.X
     */
    fun onUserAgreementClick() {
        navigate(CommonRoutes.UserAgreement)
    }

    /**
     * 点击隐私政策
     * 显示隐私政策内容
     *
     * @author Joker.X
     */
    fun onPrivacyPolicyClick() {
        navigate(CommonRoutes.PrivacyPolicy)
    }

    /**
     * 点击开源许可
     *
     * @author Joker.X
     */
    fun onOpenSourceLicenseClick() {
        toWebPage("https://github.com/Joker-x-dev/CoolMallKotlin/blob/main/LICENSE", "开源许可")
    }

    /**
     * 跳转到网页页面
     *
     * @param url 网页URL
     * @param title 页面标题
     * @author Joker.X
     */
    private fun toWebPage(url: String, title: String) {
        navigate(CommonRoutes.Web(url = url, title = title))
    }
}