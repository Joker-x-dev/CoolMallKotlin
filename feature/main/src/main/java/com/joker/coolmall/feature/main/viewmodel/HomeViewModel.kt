package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.result.Result
import com.joker.coolmall.core.common.result.asResult
import com.joker.coolmall.core.data.repository.PageRepository
import com.joker.coolmall.feature.main.state.HomeUiState
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
    private var pageRepository: PageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        getHomeData()
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
     * 获取首页数据
     */
    fun getHomeData() {
        viewModelScope.launch {
            pageRepository.getHomeData()
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value = HomeUiState.Loading
                        }

                        is Result.Success -> {
                            if (result.data.isSucceeded && result.data.data != null) {
                                val homeData = result.data
                                Timber.d("Home data: $homeData")
                                _uiState.value = HomeUiState.Success(homeData.data!!)
                            } else {
                                _uiState.value =
                                    HomeUiState.Error(result.data.message ?: "Unknown error")
                            }
                        }

                        is Result.Error -> {
                            Timber.e(result.exception)
                            _uiState.value =
                                HomeUiState.Error(result.exception.message ?: "Unknown error")
                        }
                    }
                }
        }
    }
}
