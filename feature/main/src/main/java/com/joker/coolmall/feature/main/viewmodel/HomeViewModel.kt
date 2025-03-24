package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 首页ViewModel
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel() {

    /**
     * 导航到商品详情页
     */
    fun navigateToGoodsDetail(goodsId: String) {
        viewModelScope.launch {
            navigator.navigateTo("${GoodsRoutes.DETAIL}/$goodsId")
        }
    }
}
