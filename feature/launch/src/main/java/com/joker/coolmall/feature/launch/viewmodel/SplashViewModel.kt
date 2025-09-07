/**
 * 启动页 ViewModel
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.launch.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.LaunchRoutes
import com.joker.coolmall.navigation.routes.MainRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 启动页 ViewModel
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {

    companion object {
        /**
         * 引导页已显示标记的缓存键
         */
        private const val KEY_GUIDE_SHOWN = "guide_shown"
    }

    /**
     * 检查是否已显示过引导页，并根据结果跳转到相应页面
     */
    fun checkGuideStatusAndNavigate() {
        if (isGuideShown()) {
            // 已显示过引导页，直接跳转到主页面
            toMainPage()
        } else {
            // 未显示过引导页，跳转到引导页
            toGuidePage()
        }
    }

    /**
     * 检查是否已显示过引导页
     *
     * @return true表示已显示过，false表示未显示过
     */
    private fun isGuideShown(): Boolean {
        return MMKVUtils.getBoolean(KEY_GUIDE_SHOWN, false)
    }

    /**
     * 跳转到引导页并关闭当前启动页
     */
    private fun toGuidePage() {
        toPageAndCloseCurrent(LaunchRoutes.GUIDE, LaunchRoutes.SPLASH)
    }

    /**
     * 跳转到主页并关闭当前启动页
     */
    fun toMainPage() {
        toPageAndCloseCurrent(MainRoutes.MAIN, LaunchRoutes.SPLASH)
    }
}