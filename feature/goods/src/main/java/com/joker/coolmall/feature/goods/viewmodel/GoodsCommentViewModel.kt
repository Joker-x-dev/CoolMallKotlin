package com.joker.coolmall.feature.goods.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 商品评论 ViewModel
 */
@HiltViewModel
class GoodsCommentViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState
) : BaseViewModel(navigator, appState) {
}