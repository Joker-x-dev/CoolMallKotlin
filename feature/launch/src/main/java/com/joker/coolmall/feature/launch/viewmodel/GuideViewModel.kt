package com.joker.coolmall.feature.launch.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.feature.launch.model.GuidePageProvider
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.LaunchRoutes
import com.joker.coolmall.navigation.routes.MainRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 引导页 ViewModel
 *
 * @param navigator 应用导航器
 * @param appState 应用状态
 * @param savedStateHandle 保存的状态句柄
 * @param context 应用上下文
 * @author Joker.X
 */
@HiltViewModel
class GuideViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val savedStateHandle: SavedStateHandle,
    @param:ApplicationContext private val context: Context,
) : BaseViewModel(navigator, appState) {

    companion object {
        /**
         * 引导页已显示标记的缓存键
         */
        private const val KEY_GUIDE_SHOWN = "guide_shown"
    }

    // 是否从设置页面进入（从类型安全路由获取）
    private val isFromSettings: Boolean =
        savedStateHandle.toRoute<LaunchRoutes.Guide>().fromSettings

    // 引导页数据
    val guidePages = GuidePageProvider.getGuidePages(context)

    // 当前页面索引
    private val _currentPageIndex = MutableStateFlow(0)
    val currentPageIndex: StateFlow<Int> = _currentPageIndex.asStateFlow()

    /**
     * 更新当前页面索引
     *
     * @param index 页面索引
     * @author Joker.X
     */
    fun updatePageIndex(index: Int) {
        _currentPageIndex.value = index
    }

    /**
     * 处理下一页按钮点击事件
     * 返回是否需要执行页面切换动画
     *
     * @return true表示需要切换到下一页，false表示已到最后一页
     * @author Joker.X
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
     * @author Joker.X
     */
    fun getNextPageIndex(): Int {
        val nextIndex = _currentPageIndex.value + 1
        return if (nextIndex < guidePages.size) nextIndex else _currentPageIndex.value
    }

    /**
     * 跳过引导页
     *
     * @author Joker.X
     */
    fun skipGuide() {
        startExperience()
    }

    /**
     * 开始体验应用
     *
     * @author Joker.X
     */
    private fun startExperience() {
        if (isFromSettings) {
            // 从设置页面进入，直接返回上一页
            navigateBack()
        } else {
            // 正常流程，标记引导页已显示并跳转到主页面
            markGuideAsShown()
            navigateAndCloseCurrent(
                route = MainRoutes.Main,
                currentRoute = LaunchRoutes.Guide()
            )
        }
    }

    /**
     * 标记引导页已显示过
     *
     * @author Joker.X
     */
    private fun markGuideAsShown() {
        MMKVUtils.putBoolean(KEY_GUIDE_SHOWN, true)
    }

    /**
     * 是否为最后一页
     *
     * @return 是否为最后一页
     * @author Joker.X
     */
    fun isLastPage(): Boolean {
        return _currentPageIndex.value == guidePages.size - 1
    }
}