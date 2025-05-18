package com.joker.coolmall.core.common.base.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.joker.coolmall.core.common.base.state.BaseNetWorkListUiState
import com.joker.coolmall.core.common.base.state.LoadMoreState
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.navigation.AppNavigator
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
 */
abstract class BaseNetWorkListViewModel<T : Any>(
    navigator: AppNavigator,
) : BaseViewModel(navigator) {

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
    private val _uiState = MutableStateFlow<BaseNetWorkListUiState>(BaseNetWorkListUiState.Loading)
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
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

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
        // 设置UI状态 - 仅首次加载显示加载中状态
        if (_loadMoreState.value == LoadMoreState.Loading && currentPage == 1) {
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

                // 更新加载状态
                if (newList.isEmpty()) {
                    _uiState.value = BaseNetWorkListUiState.Empty
                } else {
                    _uiState.value = BaseNetWorkListUiState.Success
                    _loadMoreState.value =
                        if (hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
                }
            }

            else -> {
                // 加载更多 - 先显示加载成功，延迟更新数据
                viewModelScope.launch {
                    _loadMoreState.value = LoadMoreState.Success
                    delay(400)
                    _listData.value = _listData.value + newList
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
    fun onRefresh() {
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
    fun onLoadMore() {
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
     * 视图层调用此方法，监听页面刷新信号。
     * @param backStackEntry 当前页面的NavBackStackEntry
     * @param key 刷新信号的key，默认是"refresh"，可自定义
     *
     * 用法：在Composable中调用 viewModel.observeRefreshState(backStackEntry, key = "refreshXXX")
     * 只需调用一次，自动去重和解绑，无内存泄漏。
     */
    fun observeRefreshState(backStackEntry: NavBackStackEntry?, key: String = "refresh") {
        if (backStackEntry == null) return
        val owner: LifecycleOwner = backStackEntry
        backStackEntry.savedStateHandle
            .getLiveData<Boolean>(key)
            .observe(owner, object : Observer<Boolean> {
                override fun onChanged(value: Boolean) {
                    if (value == true) {
                        onRefresh()
                        // 只刷新一次
                        backStackEntry.savedStateHandle[key] = false
                    }
                }
            })
    }
} 