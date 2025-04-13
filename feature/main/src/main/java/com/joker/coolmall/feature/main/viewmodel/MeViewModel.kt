package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeViewModel @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel() {
    /**
     * 跳转到登录页
     */
    fun toLogin() {
        viewModelScope.launch {
            navigator.navigateTo(AuthRoutes.HOME)
        }
    }
}
