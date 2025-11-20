package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.common.config.ThemeColorOption
import com.joker.coolmall.core.common.config.ThemePreference
import com.joker.coolmall.core.common.manager.ThemePreferenceManager
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.CommonRoutes
import com.joker.coolmall.navigation.routes.FeedbackRoutes
import com.joker.coolmall.navigation.routes.LaunchRoutes
import com.joker.coolmall.navigation.routes.UserRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 设置页面ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState
) : BaseViewModel(
    navigator = navigator,
    appState = appState
) {

    /**
     * 主题模式
     */
    val themeMode: StateFlow<ThemePreference> = ThemePreferenceManager.themeMode

    /**
     * 主题颜色
     */
    val themeColor: StateFlow<ThemeColorOption> = ThemePreferenceManager.themeColor

    /**
     * 主题模式弹窗
     */
    private val _showThemeModal = MutableStateFlow(false)
    val showThemeModal: StateFlow<Boolean> = _showThemeModal.asStateFlow()

    /**
     * 主题颜色弹窗
     */
    private val _showThemeColorModal = MutableStateFlow(false)
    val showThemeColorModal: StateFlow<Boolean> = _showThemeColorModal.asStateFlow()

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
     * 点击账号与安全
     * 跳转到个人中心页面
     *
     * @author Joker.X
     */
    fun onAccountSecurityClick() {
        navigate(UserRoutes.Profile)
    }

    /**
     * 点击意见反馈
     * 跳转到反馈列表页面
     *
     * @author Joker.X
     */
    fun onFeedbackClick() {
        navigate(FeedbackRoutes.List)
    }

    /**
     * 点击关于应用
     * 跳转到关于我们页面
     *
     * @author Joker.X
     */
    fun onAboutAppClick() {
        navigate(CommonRoutes.About)
    }

    /**
     * 点击应用引导
     * 跳转到引导页并标记从设置页面进入
     *
     * @author Joker.X
     */
    fun onAppGuideClick() {
        navigate(LaunchRoutes.Guide(fromSettings = true))
    }

    /**
     * 显示主题选择弹窗
     *
     * @author Joker.X
     */
    fun onDarkModeClick() {
        _showThemeModal.value = true
    }

    /**
     * 隐藏主题选择弹窗
     *
     * @author Joker.X
     */
    fun dismissThemeModal() {
        _showThemeModal.value = false
    }

    /**
     * 选择主题
     *
     * @param mode 选中的主题模式
     * @author Joker.X
     */
    fun onThemeModeSelected(mode: ThemePreference) {
        ThemePreferenceManager.updateThemeMode(mode)
        dismissThemeModal()
    }

    /**
     * 显示主题颜色选择弹窗
     *
     * @author Joker.X
     */
    fun onThemeColorClick() {
        _showThemeColorModal.value = true
    }

    /**
     * 隐藏主题颜色弹窗
     *
     * @author Joker.X
     */
    fun dismissThemeColorModal() {
        _showThemeColorModal.value = false
    }

    /**
     * 选择主题颜色
     *
     * @param option 选中的主题颜色选项
     * @author Joker.X
     */
    fun onThemeColorSelected(option: ThemeColorOption) {
        ThemePreferenceManager.updateThemeColor(option)
        dismissThemeColorModal()
    }
}
