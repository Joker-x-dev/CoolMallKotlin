package com.joker.coolmall.feature.common.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.feature.common.model.WebViewData
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.CommonRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 网页 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class WebViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    @param:ApplicationContext private val context: Context,
) : BaseViewModel(navigator, appState) {

    /**
     * WebView 数据
     */
    private val _webViewData = MutableStateFlow(WebViewData())
    val webViewData: StateFlow<WebViewData> = _webViewData.asStateFlow()

    /**
     * 从路由获取 WebView 路由参数
     */
    val webViewRoute = savedStateHandle.toRoute<CommonRoutes.Web>()

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

    /**
     * 刷新请求状态
     */
    private val _shouldRefresh = MutableStateFlow(false)
    val shouldRefresh: StateFlow<Boolean> = _shouldRefresh.asStateFlow()

    /**
     * 下拉菜单显示状态
     */
    private val _showDropdownMenu = MutableStateFlow(false)
    val showDropdownMenu: StateFlow<Boolean> = _showDropdownMenu.asStateFlow()

    init {
        // 从路由获取参数
        val url = webViewRoute.url
        val title = webViewRoute.title
            ?: context.getString(com.joker.coolmall.feature.common.R.string.web_title)

        if (url.isNotEmpty()) {
            _webViewData.value = WebViewData(url = url, title = title)
            _pageTitle.value = title
        }
    }

    /**
     * 更新页面标题
     *
     * @param title 新的页面标题
     * @author Joker.X
     */
    fun updatePageTitle(title: String) {
        _pageTitle.value = title
    }

    /**
     * 更新加载进度
     *
     * @param progress 加载进度(0-100)
     * @author Joker.X
     */
    fun updateProgress(progress: Int) {
        _currentProgress.value = progress
    }

    /**
     * 刷新页面
     *
     * @author Joker.X
     */
    fun refreshPage() {
        _shouldRefresh.value = true
        dismissDropdownMenu()
    }

    /**
     * 重置刷新状态
     *
     * @author Joker.X
     */
    fun resetRefreshState() {
        _shouldRefresh.value = false
    }

    /**
     * 用浏览器打开当前页面
     *
     * @author Joker.X
     */
    fun openInBrowser() {
        val currentUrl = _webViewData.value.url
        if (currentUrl.isNotEmpty()) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, currentUrl.toUri()).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                // 处理无法打开浏览器的情况
                e.printStackTrace()
            }
        }
        dismissDropdownMenu()
    }

    /**
     * 显示下拉菜单
     *
     * @author Joker.X
     */
    fun showDropdownMenu() {
        _showDropdownMenu.value = true
    }

    /**
     * 隐藏下拉菜单
     *
     * @author Joker.X
     */
    fun dismissDropdownMenu() {
        _showDropdownMenu.value = false
    }
}