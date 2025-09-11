package com.joker.coolmall.feature.feedback.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 提交反馈 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class FeedbackSubmitViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {
}