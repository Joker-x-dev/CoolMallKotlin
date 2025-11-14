package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.NavigationResultKey
import com.joker.coolmall.navigation.RefreshResultKey
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 网络请求列表ViewModel基类
 *
 * 专门处理列表数据的加载、分页、刷新和加载更多功能
 * 封装了常见的列表操作逻辑，简化子类实现
 *
 * @param T 列表项数据类型
 * @param navigator 导航控制器
 * @param appState 应用状态
 * @author Joker.X
 */
abstract class BaseNetWorkListViewModel<T : Any>(
    navigator: AppNavigator,
    appState: AppState
) : BaseViewModel(navigator, appState) {

    /**
     * 当前页码
     */
    protected var currentPage = 1

    /**
     * 每页数量
     */
    protected val pageSize = 10

    /**
     * 网络请求UI状态
     */
    val _uiState = MutableStateFlow<BaseNetWorkListUiState>(BaseNetWorkListUiState.Loading)
    val uiState: StateFlow<BaseNetWorkListUiState> = _uiState.asStateFlow()

    /**
     * 列表数据
     */
    protected val _listData = MutableStateFlow<List<T>>(emptyList())
    val listData: StateFlow<List<T>> = _listData.asStateFlow()

    /**
     * 加载更多状态
     */
    protected val _loadMoreState = MutableStateFlow<LoadMoreState>(LoadMoreState.PullToLoad)
    val loadMoreState: StateFlow<LoadMoreState> = _loadMoreState.asStateFlow()

    /**
     * 下拉刷新状态 (仅用于PullToRefresh组件)
     */
    val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    /**
     * 是否启用最少加载时间（240毫秒）
     * 子类可重写此属性以启用最少加载时间功能
     */
    protected open val enableMinLoadingTime: Boolean = false

    /**
     * 请求开始时间，用于计算最少加载时间（仅首次加载）
     */
    private var requestStartTime: Long = 0

    /**
     * 子类必须实现此方法，提供分页API请求的Flow
     *
     * @return 返回包含分页数据的Flow
     */
    protected abstract fun requestListData(): Flow<NetworkResponse<NetworkPageData<T>>>

    /**
     * 初始化函数，在子类init块中调用
     */
    protected fun initLoad() {
        loadListData()
    }

    /**
     * 加载列表数据
     */
    protected fun loadListData() {

        val isFirstLoading = _loadMoreState.value == LoadMoreState.Loading && currentPage == 1

        // 记录请求开始时间（仅首次加载）并且启用最少加载时间功能
        if (isFirstLoading && enableMinLoadingTime) {
            requestStartTime = System.currentTimeMillis()
        }

        // 设置UI状态 - 仅首次加载显示加载中状态
        if (isFirstLoading) {
            _uiState.value = BaseNetWorkListUiState.Loading
        }

        ResultHandler.handleResult(
            showToast = false,
            scope = viewModelScope,
            flow = requestListData().asResult(),
            onSuccess = { response ->
                handleSuccess(response.data)
            },
            onError = { message, exception ->
                handleError(message, exception)
            }
        )
    }

    /**
     * 处理成功响应
     */
    protected open fun handleSuccess(data: NetworkPageData<T>?) {
        val newList = data?.list ?: emptyList()
        val pagination = data?.pagination

        // 计算是否还有下一页数据
        val hasNextPage = if (pagination != null) {
            val total = pagination.total ?: 0
            val size = pagination.size ?: pageSize
            val currentPageNum = pagination.page ?: currentPage

            // 当前页的数据量 * 当前页码 < 总数据量，说明还有下一页
            size * currentPageNum < total
        } else {
            false
        }

        when {
            currentPage == 1 -> {
                // 刷新或首次加载 - 重置列表
                _listData.value = newList
                _isRefreshing.value = false

                // 判断是否需要最少加载时间延迟
                if (enableMinLoadingTime) {
                    val elapsedTime = System.currentTimeMillis() - requestStartTime
                    val minLoadingTime = 240L

                    if (elapsedTime < minLoadingTime) {
                        // 延迟设置成功状态
                        viewModelScope.launch {
                            delay(minLoadingTime - elapsedTime)
                            setFirstLoadSuccessState(newList, hasNextPage)
                        }
                    } else {
                        setFirstLoadSuccessState(newList, hasNextPage)
                    }
                } else {
                    setFirstLoadSuccessState(newList, hasNextPage)
                }
            }

            else -> {
                // 加载更多 - 先显示加载成功，延迟更新数据
                viewModelScope.launch {
                    _loadMoreState.value = LoadMoreState.Success
                    delay(400)
                    _listData.value += newList
                    _loadMoreState.value =
                        if (hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
                }
            }
        }
    }

    /**
     * 处理错误响应
     */
    protected open fun handleError(message: String?, exception: Throwable?) {
        _isRefreshing.value = false

        if (currentPage == 1) {
            // 首次加载或刷新失败
            if (_listData.value.isEmpty()) {
                _uiState.value = BaseNetWorkListUiState.Error
            }
            _loadMoreState.value = LoadMoreState.PullToLoad
        } else {
            // 加载更多失败，回退页码
            currentPage--
            _loadMoreState.value = LoadMoreState.Error
        }
    }

    /**
     * 重试请求
     */
    fun retryRequest() {
        currentPage = 1
        _loadMoreState.value = LoadMoreState.Loading
        loadListData()
    }

    /**
     * 触发下拉刷新
     */
    open fun onRefresh() {
        // 如果正在加载中，则不重复请求
        if (_loadMoreState.value == LoadMoreState.Loading) {
            return
        }

        _isRefreshing.value = true
        currentPage = 1
        loadListData()
    }

    /**
     * 加载更多数据
     */
    open fun onLoadMore() {
        // 只有在可加载更多和加载失败状态下才能触发加载
        if (_loadMoreState.value == LoadMoreState.Loading ||
            _loadMoreState.value == LoadMoreState.NoMore ||
            _loadMoreState.value == LoadMoreState.Success
        ) {
            return
        }

        _loadMoreState.value = LoadMoreState.Loading
        currentPage++
        loadListData()
    }

    /**
     * 判断是否应该触发加载更多
     * 显示的最后一项索引接近列表末尾（倒数第3个）
     *
     * @param lastIndex 当前可见的最后一项索引
     * @param totalCount 列表总项数
     * @return 是否应该触发加载更多
     */
    fun shouldTriggerLoadMore(lastIndex: Int, totalCount: Int): Boolean {
        return lastIndex >= totalCount - 3 &&
                loadMoreState.value != LoadMoreState.Loading &&
                loadMoreState.value != LoadMoreState.NoMore &&
                listData.value.isNotEmpty()
    }

    /**
     * 设置首次加载成功状态
     */
    private fun setFirstLoadSuccessState(newList: List<T>, hasNextPage: Boolean) {
        // 更新加载状态
        if (newList.isEmpty()) {
            _uiState.value = BaseNetWorkListUiState.Empty
        } else {
            _uiState.value = BaseNetWorkListUiState.Success
            _loadMoreState.value =
                if (hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
        }
    }

    /**
     * 视图层调用此方法，监听页面刷新信号（基于 NavigationResultKey）。
     *
     * @param backStackEntry 当前页面的 NavBackStackEntry
     * @param key 刷新结果的类型安全 Key，默认使用全局的 [RefreshResultKey]
     *
     * 用法：在 Composable 中调用
     * ```kotlin
     * val backStackEntry = navController.currentBackStackEntry
     * LaunchedEffect(backStackEntry) {
     *     viewModel.observeRefreshState(backStackEntry)
     * }
     * ```
     *
     * 只需调用一次，自动去重和解绑，无内存泄漏。
     * 语义等价于旧方案中的 "refresh" 布尔标记。
     */
    fun observeRefreshState(
        backStackEntry: NavBackStackEntry?,
        key: NavigationResultKey<Boolean> = RefreshResultKey
    ) {
        if (backStackEntry == null) return
        val owner: LifecycleOwner = backStackEntry
        backStackEntry.savedStateHandle
            .getLiveData<Boolean>(key.key)
            .observe(owner, Observer<Boolean> { value ->
                if (value) {
                    onRefresh()
                    // 只刷新一次
                    backStackEntry.savedStateHandle[key.key] = false
                }
            })
    }
}