package com.joker.coolmall.feature.cs.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 客服聊天 ViewModel
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    navigator: AppNavigator,
) : BaseViewModel(navigator) {
    init {

    }
}