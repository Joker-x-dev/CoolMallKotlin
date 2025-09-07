/**
 * 引导页 ViewModel
 *
 * @author Joker.X
 */
package com.joker.coolmall.feature.launch.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.feature.launch.model.GuidePageProvider
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.LaunchRoutes
import com.joker.coolmall.navigation.routes.MainRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 引导页 ViewModel
 */
@HiltViewModel
class GuideViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {

    companion object {
        /**
         * 引导页已显示标记的缓存键
         */
        private const val KEY_GUIDE_SHOWN = "guide_shown"
    }

    // 引导页数据
    val guidePages = GuidePageProvider.getGuidePages()

    // 当前页面索引
    private val _currentPageIndex = MutableStateFlow(0)
    val currentPageIndex: StateFlow<Int> = _currentPageIndex.asStateFlow()

    /**
     * 更新当前页面索引
     *
     * @param index 页面索引
     */
    fun updatePageIndex(index: Int) {
        _currentPageIndex.value = index
    }

    /**
     * 处理下一页按钮点击事件
     * 返回是否需要执行页面切换动画
     *
     * @return true表示需要切换到下一页，false表示已到最后一页
     */
    fun handleNextClick(): Boolean {
        return if (isLastPage()) {
            // 最后一页，开始体验
            startExperience()
            false
        } else {
            // 需要切换到下一页
            true
        }
    }

    /**
     * 获取下一页索引
     *
     * @return 下一页索引，如果已是最后一页则返回当前索引
     */
    fun getNextPageIndex(): Int {
        val nextIndex = _currentPageIndex.value + 1
        return if (nextIndex < guidePages.size) nextIndex else _currentPageIndex.value
    }

    /**
     * 跳过引导页
     */
    fun skipGuide() {
        startExperience()
    }

    /**
     * 开始体验应用
     */
    private fun startExperience() {
        // 标记引导页已显示
        markGuideAsShown()
        // 跳转到主页面并关闭引导页
        toPageAndCloseCurrent(MainRoutes.MAIN, LaunchRoutes.GUIDE)
    }

    /**
     * 标记引导页已显示过
     */
    private fun markGuideAsShown() {
        MMKVUtils.putBoolean(KEY_GUIDE_SHOWN, true)
    }

    /**
     * 是否为最后一页
     *
     * @return 是否为最后一页
     */
    fun isLastPage(): Boolean {
        return _currentPageIndex.value == guidePages.size - 1
    }
}