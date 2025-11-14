package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.CouponRepository
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.repository.PageRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.Home
import com.joker.coolmall.core.model.request.GoodsSearchRequest
import com.joker.coolmall.core.model.request.ReceiveCouponRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.AuthRoutes
import com.joker.coolmall.navigation.routes.CommonRoutes
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
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param pageRepository 页面仓库
 * @param goodsRepository 商品仓库
 * @param couponRepository 优惠券仓库
 * @author Joker.X
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val pageRepository: PageRepository,
    private val goodsRepository: GoodsRepository,
    private val couponRepository: CouponRepository
) : BaseNetWorkListViewModel<Goods>(navigator, appState) {

    /**
     * 页面数据
     */
    private val _pageData = MutableStateFlow<Home>(Home())
    val pageData: StateFlow<Home> = _pageData.asStateFlow()

    /**
     * 优惠券领取弹出层的显示状态
     */
    private val _couponModalVisible = MutableStateFlow(false)
    val couponModalVisible: StateFlow<Boolean> = _couponModalVisible.asStateFlow()

    init {
        loadHomeData()
    }

    /**
     * 重写请求列表数据方法
     *
     * @return 商品分页数据流
     * @author Joker.X
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
     *
     * @author Joker.X
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
     *
     * @author Joker.X
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
     *
     * @author Joker.X
     */
    fun toGoodsSearch() {
        navigate(GoodsRoutes.Search)
    }

    /**
     * 导航到商品详情页
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    fun toGoodsDetail(goodsId: Long) {
        navigate(GoodsRoutes.Detail(goodsId = goodsId))
    }

    /**
     * 跳转到商品分类页面
     *
     * @param categoryId 点击的分类ID
     * @author Joker.X
     */
    fun toGoodsCategoryPage(categoryId: Long) {
        val data = pageData.value
        val childCategoryIds =
            findChildCategoryIds(categoryId, data.categoryAll ?: emptyList())
        // 如果有子分类，传递子分类ID
        val typeIdParam = childCategoryIds.joinToString(",")
        navigate(GoodsRoutes.Category(typeId = typeIdParam, featured = false, recommend = false, keyword = null))
    }

    /**
     * 跳转到限时精选页面
     *
     * @author Joker.X
     */
    fun toFlashSalePage() {
        navigate(GoodsRoutes.Category(typeId = null, featured = true, recommend = false, keyword = null))
    }

    /**
     * 跳转到推荐商品页面
     *
     * @author Joker.X
     */
    fun toRecommendPage() {
        navigate(GoodsRoutes.Category(typeId = null, featured = false, recommend = true, keyword = null))
    }

    /**
     * 跳转到 GitHub 页面
     *
     * @author Joker.X
     */
    fun toGitHubPage() {
        val url = "https://github.com/Joker-x-dev/CoolMallKotlin"
        val title = "GitHub"
        navigate(CommonRoutes.Web(url = url, title = title))
    }

    /**
     * 跳转到关于页面
     *
     * @author Joker.X
     */
    fun toAboutPage() {
        navigate(CommonRoutes.About)
    }

    /**
     * 显示优惠券弹出层
     *
     * @author Joker.X
     */
    fun showCouponModal() {
        _couponModalVisible.value = true
    }

    /**
     * 隐藏优惠券领取弹出层
     *
     * @author Joker.X
     */
    fun hideCouponModal() {
        _couponModalVisible.value = false
    }

    /**
     * 领取优惠券
     *
     * @param coupon 要领取的优惠券
     * @author Joker.X
     */
    fun receiveCoupon(coupon: Coupon) {
        // 检查登录状态
        if (!appState.isLoggedIn.value) {
            hideCouponModal()
            // 未登录，跳转到登录页面
            navigate(AuthRoutes.Login)
            return
        }

        val request = ReceiveCouponRequest(couponId = coupon.id)
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = couponRepository.receiveCoupon(request).asResult(),
            showToast = true,
            onData = { data -> ToastUtils.showSuccess(data) },
        )
    }

    /**
     * 查找指定分类的所有子分类ID
     *
     * @param parentId 父分类ID
     * @param allCategories 所有分类列表
     * @return 子分类ID列表
     * @author Joker.X
     */
    private fun findChildCategoryIds(parentId: Long, allCategories: List<Category>): List<Long> {
        return allCategories.filter { it.parentId == parentId.toInt() }.map { it.id }
    }
}