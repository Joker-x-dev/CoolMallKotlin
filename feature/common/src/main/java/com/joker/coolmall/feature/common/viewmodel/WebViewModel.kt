package com.joker.coolmall.feature.common.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.feature.common.model.WebViewData
import com.joker.coolmall.feature.common.navigation.WebRoutes
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 网页 ViewModel
 */
@HiltViewModel
class WebViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(navigator, appState) {

    /**
     * WebView 数据
     */
    private val _webViewData = MutableStateFlow<WebViewData>(WebViewData())
    val webViewData: StateFlow<WebViewData> = _webViewData.asStateFlow()

    /**
     * 页面标题
     */
    private val _pageTitle = MutableStateFlow("")
    val pageTitle: StateFlow<String> = _pageTitle.asStateFlow()

    /**
     * 加载进度
     */
    private val _currentProgress = MutableStateFlow(0)
    val currentProgress: StateFlow<Int> = _currentProgress.asStateFlow()

    init {
        // 从路由参数获取 URL
        val url = savedStateHandle.get<String>(WebRoutes.URL_ARG) ?: ""
        val title = savedStateHandle.get<String>(WebRoutes.TITLE_ARG) ?: "网页"
        
        if (url.isNotEmpty()) {
            _webViewData.value = WebViewData(url = url, title = title)
            _pageTitle.value = title
        }
    }

    /**
     * 更新页面标题
     */
    fun updatePageTitle(title: String) {
        _pageTitle.value = title
    }

    /**
     * 更新加载进度
     */
    fun updateProgress(progress: Int) {
        _currentProgress.value = progress
    }
} 