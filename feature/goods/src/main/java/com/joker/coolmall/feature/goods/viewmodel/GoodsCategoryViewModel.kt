package com.joker.coolmall.feature.goods.viewmodel

import CategoryUiState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkListViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.CategoryTree
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.request.GoodsSearchRequest
import com.joker.coolmall.core.model.response.NetworkPageData
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.goods.model.SortState
import com.joker.coolmall.feature.goods.model.SortType
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 商品分类 ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param savedStateHandle 保存的状态句柄
 * @param goodsRepository 商品仓库
 * @author Joker.X
 */
@HiltViewModel
class GoodsCategoryViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository
) : BaseNetWorkListViewModel<Goods>(navigator, appState) {

    /**
     * 分类UI状态
     */
    private val _categoryUiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState

    /**
     * 筛选对话框可见性状态
     */
    private val _filtersVisible = MutableStateFlow(false)
    val filtersVisible: StateFlow<Boolean> = _filtersVisible

    /**
     * 分类数据是否已加载
     */
    private var categoryDataLoaded = false

    /**
     * 选中的分类ID列表
     */
    private val _selectedCategoryIds = MutableStateFlow<List<Long>>(emptyList())
    val selectedCategoryIds: StateFlow<List<Long>> = _selectedCategoryIds

    /**
     * 最低价格
     */
    private val _minPrice = MutableStateFlow("")
    val minPrice: StateFlow<String> = _minPrice

    /**
     * 最高价格
     */
    private val _maxPrice = MutableStateFlow("")
    val maxPrice: StateFlow<String> = _maxPrice

    /**
     * 当前选中的排序类型
     */
    private val _currentSortType = MutableStateFlow(SortType.COMPREHENSIVE)
    val currentSortType: StateFlow<SortType> = _currentSortType

    /**
     * 当前排序状态
     */
    private val _currentSortState = MutableStateFlow(SortState.NONE)
    val currentSortState: StateFlow<SortState> = _currentSortState

    /**
     * 搜索关键词
     */
    private var searchKeyword = ""

    /**
     * 获取当前搜索关键词
     *
     * @return 搜索关键词
     * @author Joker.X
     */
    fun getCurrentSearchKeyword(): String = searchKeyword

    /**
     * 是否为网格布局
     */
    private val _isGridLayout = MutableStateFlow(true)
    val isGridLayout: StateFlow<Boolean> = _isGridLayout

    /**
     * 是否为限时精选
     */
    private var isFeatured = false

    /**
     * 是否为推荐商品
     */
    private var isRecommend = false

    init {
        // 从路由获取参数
        val route = savedStateHandle.toRoute<GoodsRoutes.Category>()
        
        // 从路由参数获取分类ID并设置
        val typeIdParam = route.typeId
        if (!typeIdParam.isNullOrBlank()) {
            val categoryIds = typeIdParam.split(",")
                .mapNotNull { it.trim().toLongOrNull() }
            _selectedCategoryIds.value = categoryIds
        }

        // 从路由参数获取featured参数
        isFeatured = route.featured

        // 从路由参数获取recommend参数
        isRecommend = route.recommend

        // 从路由参数获取搜索关键词
        searchKeyword = route.keyword ?: ""

        // 从路由参数获取最小金额（用于优惠券跳转）
        route.minPrice?.let {
            _minPrice.value = it
        }

        // 加载数据
        initLoad()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     *
     * @return 商品分页数据的网络响应Flow
     * @author Joker.X
     */
    override fun requestListData(): Flow<NetworkResponse<NetworkPageData<Goods>>> {
        val (order, sort) = when (_currentSortType.value) {
            SortType.COMPREHENSIVE -> null to null
            SortType.SALES -> {
                when (_currentSortState.value) {
                    SortState.ASC -> "sold" to "asc"
                    SortState.DESC -> "sold" to "desc"
                    SortState.NONE -> null to null
                }
            }

            SortType.PRICE -> {
                when (_currentSortState.value) {
                    SortState.ASC -> "price" to "asc"
                    SortState.DESC -> "price" to "desc"
                    SortState.NONE -> null to null
                }
            }
        }

        return goodsRepository.getGoodsPage(
            GoodsSearchRequest(
                page = super.currentPage,
                size = super.pageSize,
                featured = isFeatured,
                recommend = isRecommend,
                typeId = _selectedCategoryIds.value.takeIf { it.isNotEmpty() },
                minPrice = _minPrice.value.takeIf { it.isNotBlank() },
                maxPrice = _maxPrice.value.takeIf { it.isNotBlank() },
                order = order,
                sort = sort,
                keyWord = searchKeyword.takeIf { it.isNotBlank() }
            )
        )
    }

    /**
     * 显示筛选对话框
     *
     * @author Joker.X
     */
    fun showFilters() {
        _filtersVisible.value = true
        // 只在第一次显示时加载分类数据
        if (categoryDataLoaded) return
        // 计时 360 毫秒等待动画结束后加载分类数据
        viewModelScope.launch {
            delay(340)
            loadGoodsCategory()
        }
    }

    /**
     * 隐藏筛选对话框
     *
     * @author Joker.X
     */
    fun hideFilters() {
        _filtersVisible.value = false
    }

    /**
     * 应用筛选条件并刷新数据
     *
     * @param categoryIds 分类ID列表
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @author Joker.X
     */
    fun applyFilters(categoryIds: List<Long>, minPrice: String, maxPrice: String) {
        _selectedCategoryIds.value = categoryIds
        _minPrice.value = minPrice
        _maxPrice.value = maxPrice
        hideFilters()
        // 重新加载数据
        onRefresh()
    }

    /**
     * 重置筛选条件
     *
     * @author Joker.X
     */
    fun resetFilters() {
        _selectedCategoryIds.value = emptyList()
        _minPrice.value = ""
        _maxPrice.value = ""
        // 重新加载数据
        onRefresh()
    }

    /**
     * 处理排序点击事件
     *
     * @param sortType 点击的排序类型
     * @author Joker.X
     */
    fun onSortClick(sortType: SortType) {
        when (sortType) {
            SortType.COMPREHENSIVE -> {
                // 综合排序：直接设置为综合，状态为NONE
                _currentSortType.value = SortType.COMPREHENSIVE
                _currentSortState.value = SortState.NONE
            }

            SortType.SALES, SortType.PRICE -> {
                if (_currentSortType.value == sortType) {
                    // 同一个排序类型，切换状态：NONE -> ASC -> DESC -> NONE
                    _currentSortState.value = when (_currentSortState.value) {
                        SortState.NONE -> SortState.ASC
                        SortState.ASC -> SortState.DESC
                        SortState.DESC -> SortState.NONE
                    }
                    // 如果状态变为NONE，则切换回综合排序
                    if (_currentSortState.value == SortState.NONE) {
                        _currentSortType.value = SortType.COMPREHENSIVE
                    }
                } else {
                    // 不同排序类型，设置新的排序类型并设置为ASC
                    _currentSortType.value = sortType
                    _currentSortState.value = SortState.ASC
                }
            }
        }
        // 重新加载数据
        onRefresh()
    }

    /**
     * 搜索
     *
     * @param keyword 搜索关键词
     * @author Joker.X
     */
    fun onSearch(keyword: String) {
        searchKeyword = keyword
        onRefresh()
    }

    /**
     * 切换布局模式
     *
     * @author Joker.X
     */
    fun toggleLayoutMode() {
        _isGridLayout.value = !_isGridLayout.value
    }

    /**
     * 加载商品分类
     *
     * @author Joker.X
     */
    private fun loadGoodsCategory() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = goodsRepository.getGoodsTypeList().asResult(),
            showToast = true,
            onLoading = { _categoryUiState.value = CategoryUiState.Loading },
            onData = { data ->
                val categoryTree = convertToTree(data)
                _categoryUiState.value = CategoryUiState.Success(categoryTree)
                categoryDataLoaded = true
            },
            onError = { message, exception ->
                _categoryUiState.value = CategoryUiState.Error()
            }
        )
    }

    /**
     * 跳转到商品详情页面
     *
     * @param goodsId 商品ID
     * @author Joker.X
     */
    fun toGoodsDetailPage(goodsId: Long) {
        navigate(GoodsRoutes.Detail(goodsId = goodsId))
    }

    /**
     * 将Category列表转换为CategoryTree树形结构
     *
     * @param categories 原始分类列表
     * @return 树形结构的分类列表
     * @author Joker.X
     */
    private fun convertToTree(categories: List<Category>): List<CategoryTree> {
        // 按sortNum排序的列表
        val sortedList = categories.sortedBy { it.sortNum }

        // 将Category转换为CategoryTree
        val categoryTrees = sortedList.map { CategoryTree.fromCategory(it) }

        // 临时存储，key是id，value是该分类在categoryTrees中的索引
        val categoryMap = mutableMapOf<Long, Int>()

        // 初始化映射
        categoryTrees.forEachIndexed { index, categoryTree ->
            categoryMap[categoryTree.id] = index
        }

        // 找出顶级分类
        val rootCategories = mutableListOf<CategoryTree>()

        // 子分类映射，key是父ID，value是子分类列表
        val childrenMap = mutableMapOf<Long, MutableList<CategoryTree>>()

        // 将分类按父ID分组
        categoryTrees.forEach { categoryTree ->
            val parentId = categoryTree.parentId
            if (parentId == null) {
                // 顶级分类
                rootCategories.add(categoryTree)
            } else {
                // 添加到子分类映射
                val children = childrenMap.getOrPut(parentId.toLong()) { mutableListOf() }
                children.add(categoryTree)
            }
        }

        // 递归构建树
        return rootCategories.map { rootCategory ->
            buildCategoryTree(rootCategory, childrenMap)
        }
    }

    /**
     * 递归构建分类树
     *
     * @param categoryTree 当前分类树节点
     * @param childrenMap 子分类映射
     * @return 构建好的分类树
     * @author Joker.X
     */
    private fun buildCategoryTree(
        categoryTree: CategoryTree,
        childrenMap: Map<Long, List<CategoryTree>>
    ): CategoryTree {
        val children = childrenMap[categoryTree.id] ?: emptyList()

        // 如果没有子分类，直接返回
        if (children.isEmpty()) {
            return categoryTree
        }

        // 递归构建每个子分类的树
        val childrenTree = children.map { child ->
            buildCategoryTree(child, childrenMap)
        }

        // 创建一个新的CategoryTree对象，包含子分类列表
        return categoryTree.copy(children = childrenTree)
    }
}