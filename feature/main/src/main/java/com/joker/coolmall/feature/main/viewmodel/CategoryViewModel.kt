package com.joker.coolmall.feature.main.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseNetWorkViewModel
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.model.Category
import com.joker.coolmall.core.model.response.NetworkResponse
import com.joker.coolmall.feature.main.model.CategoryTree
import com.joker.coolmall.feature.main.state.CategoryUiState
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    navigator: AppNavigator,
    private val goodsRepository: GoodsRepository
) : BaseNetWorkViewModel<List<Category>>(
    navigator = navigator
) {

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
        super.executeRequest()
    }

    /**
     * 通过重写来给父类提供API请求的Flow
     */
    override fun requestApiFlow(): Flow<NetworkResponse<List<Category>>> {
        return goodsRepository.getGoodsTypeList()
    }

    /**
     * 重写请求成功相关逻辑来处理数据
     */
    override fun onRequestSuccess(data: List<Category>) {
        val categoryTree = convertToTree(data)
        _categoryUiState.value = CategoryUiState.Success(categoryTree)
    }

    /**
     * 重写请求失败相关逻辑
     */
    override fun onRequestError(message: String, exception: Throwable) {
        _categoryUiState.value = CategoryUiState.Error()
    }

    /**
     * 重写设置加载状态相关逻辑
     */
    override fun setLoadingState() {
        _categoryUiState.value = CategoryUiState.Loading
    }

    /**
     * 选择分类
     * @param index 选中的分类索引
     */
    fun selectCategory(index: Int) {
        _selectedCategoryIndex.value = index
    }

    /**
     * 将Category列表转换为CategoryTree树形结构
     * @param categories 原始分类列表
     * @return 树形结构的分类列表
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
     * @param categoryTree 当前分类树节点
     * @param childrenMap 子分类映射
     * @return 构建好的分类树
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
