package com.joker.coolmall.feature.feedback.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.FeedbackRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 反馈列表 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class FeedbackListViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {
    /**
     * 导航到提交反馈页面
     */
    fun toFeedbackSubmitPage() {
        toPage(FeedbackRoutes.SUBMIT)
    }
}