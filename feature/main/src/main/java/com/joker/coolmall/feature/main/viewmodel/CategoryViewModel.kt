package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.model.entity.Category
import com.joker.coolmall.core.model.entity.CategoryTree
import com.joker.coolmall.feature.main.state.CategoryUiState
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.navigation.routes.GoodsRoutes
import com.joker.coolmall.result.ResultHandler
import com.joker.coolmall.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * 分类页面ViewModel
 *
 * @param navigator 导航器
 * @param appState 应用状态
 * @param goodsRepository 商品仓库
 * @author Joker.X
 */
@HiltViewModel
class CategoryViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
    private val goodsRepository: GoodsRepository
) : BaseViewModel(navigator, appState) {

    /**
     * 分类UI状态
     */
    private val _categoryUiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState

    /**
     * 当前选中的左侧分类索引
     */
    private val _selectedCategoryIndex = MutableStateFlow(0)
    val selectedCategoryIndex: StateFlow<Int> = _selectedCategoryIndex

    init {
        loadCategoryData()
    }

    /**
     * 获取分类数据
     *
     * @author Joker.X
     */
    fun loadCategoryData() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = goodsRepository.getGoodsTypeList().asResult(),
            showToast = true,
            onLoading = { _categoryUiState.value = CategoryUiState.Loading },
            onData = { data ->
                val categoryTree = convertToTree(data)
                _categoryUiState.value = CategoryUiState.Success(categoryTree)
            },
            onError = { message, exception ->
                _categoryUiState.value = CategoryUiState.Error()
            }
        )
    }

    /**
     * 重试加载分类数据
     *
     * @author Joker.X
     */
    fun retryRequest() {
        loadCategoryData()
    }

    /**
     * 选择分类
     *
     * @param index 选中的分类索引
     * @author Joker.X
     */
    fun selectCategory(index: Int) {
        _selectedCategoryIndex.value = index
    }

    /**
     * 跳转到商品分类页面
     *
     * @param categoryId 点击的分类ID
     * @author Joker.X
     */
    fun toGoodsCategoryPage(categoryId: Long) {
        navigate(GoodsRoutes.Category(typeId = categoryId.toString(), featured = false, recommend = false, keyword = null))
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
