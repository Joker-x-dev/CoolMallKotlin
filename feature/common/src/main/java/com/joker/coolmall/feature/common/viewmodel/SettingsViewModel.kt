package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.CommonRoutes
import com.joker.coolmall.navigation.routes.FeedbackRoutes
import com.joker.coolmall.navigation.routes.LaunchRoutes
import com.joker.coolmall.navigation.routes.UserRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
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
     * 点击用户协议
     * 显示用户使用协议
     *
     * @author Joker.X
     */
    fun onUserAgreementClick() {
        toPage(CommonRoutes.USER_AGREEMENT)
    }

    /**
     * 点击隐私政策
     * 显示隐私政策内容
     *
     * @author Joker.X
     */
    fun onPrivacyPolicyClick() {
        toPage(CommonRoutes.PRIVACY_POLICY)
    }

    /**
     * 点击账号与安全
     * 跳转到个人中心页面
     *
     * @author Joker.X
     */
    fun onAccountSecurityClick() {
        toPage(UserRoutes.PROFILE)
    }

    /**
     * 点击意见反馈
     * 跳转到反馈列表页面
     *
     * @author Joker.X
     */
    fun onFeedbackClick() {
        toPage(FeedbackRoutes.LIST)
    }

    /**
     * 点击关于应用
     * 跳转到关于我们页面
     *
     * @author Joker.X
     */
    fun onAboutAppClick() {
        toPage(CommonRoutes.ABOUT)
    }

    /**
     * 点击应用引导
     * 跳转到引导页并标记从设置页面进入
     *
     * @author Joker.X
     */
    fun onAppGuideClick() {
        toPage("${LaunchRoutes.GUIDE}?from_settings=true")
    }
}