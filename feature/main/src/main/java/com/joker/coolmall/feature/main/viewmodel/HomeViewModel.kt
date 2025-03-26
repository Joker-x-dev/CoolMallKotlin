package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.result.Result
import com.joker.coolmall.core.common.result.asResult
import com.joker.coolmall.core.data.repository.BannerRepository
import com.joker.coolmall.core.model.Banner
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 首页ViewModel
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private var bannerRepository: BannerRepository
) : ViewModel() {

    private val _datum = MutableStateFlow<List<Banner>>(emptyList())
    val datum: StateFlow<List<Banner>> = _datum

    init {
        getBannerList()
    }

    /**
     * 导航到商品详情页
     */
    fun navigateToGoodsDetail(goodsId: String) {
        viewModelScope.launch {
            navigator.navigateTo("${GoodsRoutes.DETAIL}/$goodsId")
        }
    }

    /**
     * 获取轮播图列表
     */
    fun getBannerList() {
        viewModelScope.launch {
            bannerRepository.getBannerList()
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> {
                        }

                        is Result.Success -> {
                            val bannerList = result.data.data?.list
                            Timber.d("Banner list data: $bannerList")
                            _datum.value = bannerList ?: emptyList()
                        }

                        is Result.Error -> {
                            Timber.e(result.exception)
                        }
                    }
                }
        }
    }
}
