package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.repository.PageRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.request.GoodsSearchRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 首页ViewModel
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val pageRepository: PageRepository,
    private val goodsRepository: GoodsRepository
) : BaseNetWorkListViewModel<Goods>(
    navigator = navigator,
    appState = appState
) {

    /**
     * 页面数据
     */
    private val _pageData = MutableStateFlow<Home>(Home())
    val pageData: StateFlow<Home> = _pageData.asStateFlow()

    init {
        loadHomeData()
    }

    /**
     * 重写请求列表数据方法
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Goods>>> {
        return goodsRepository.getGoodsPage(
            GoodsSearchRequest(
                page = super.currentPage,
                size = super.pageSize
            )
        )
    }

    /**
     * 加载首页数据
     */
    fun loadHomeData() {
        ResultHandler.handleResult(
            showToast = false,
            scope = viewModelScope,
            flow = pageRepository.getHomeData().asResult(),
            onSuccess = { response ->
                _pageData.value = response.data ?: Home()
                _isRefreshing.value = false

                // 使用首页数据中的商品初始化列表
                _listData.value = response.data?.goods ?: emptyList()

                // 更新上拉加载状态
                _loadMoreState.value =
                    if (response.data?.goods?.isNotEmpty() == true) LoadMoreState.PullToLoad
                    else LoadMoreState.NoMore

                // 设置初始状态
                super._uiState.value = BaseNetWorkListUiState.Success

            },
            onError = { _, _ ->
                super._uiState.value = BaseNetWorkListUiState.Error
            }
        )
    }

    /**
     * 重写触发下拉刷新方法
     */
    override fun onRefresh() {
        // 如果正在加载中，则不重复请求
        if (_loadMoreState.value == LoadMoreState.Loading) {
            return
        }

        _isRefreshing.value = true
        currentPage = 1
        loadHomeData()
    }

    /**
     * 跳转到商品搜索页面
     */
    fun toGoodsSearch() {
        super.toPage(GoodsRoutes.SEARCH)
    }

    /**
     * 导航到商品详情页
     */
    fun toGoodsDetail(goodsId: Long) {
        super.toPage(GoodsRoutes.DETAIL, goodsId)
    }

    /**
     * 跳转到商品分类页面
     * @param categoryId 点击的分类ID
     */
    fun toGoodsCategoryPage(categoryId: Long) {
        val data = pageData.value
        val childCategoryIds =
            findChildCategoryIds(categoryId, data.categoryAll ?: emptyList())
        // 如果有子分类，传递子分类ID
        val typeIdParam = childCategoryIds.joinToString(",")
        super.toPage("${GoodsRoutes.CATEGORY}?type_id=$typeIdParam")
    }

    /**
     * 跳转到限时精选页面
     */
    fun toFlashSalePage() {
        super.toPage("${GoodsRoutes.CATEGORY}?featured=true")
    }

    /**
     * 跳转到推荐商品页面
     */
    fun toRecommendPage() {
        super.toPage("${GoodsRoutes.CATEGORY}?recommend=true")
    }

    /**
     * 查找指定分类的所有子分类ID
     * @param parentId 父分类ID
     * @param allCategories 所有分类列表
     * @return 子分类ID列表
     */
    private fun findChildCategoryIds(parentId: Long, allCategories: List<Category>): List<Long> {
        return allCategories.filter { it.parentId == parentId.toInt() }.map { it.id }
    }
}