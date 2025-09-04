/**
 * 启动页 ViewModel
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.launch.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
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

    /**
     * 跳转到主页并关闭当前启动页
     */
    fun toMainPage() {
        toPageAndCloseCurrent(MainRoutes.MAIN, LaunchRoutes.SPLASH)
    }
}