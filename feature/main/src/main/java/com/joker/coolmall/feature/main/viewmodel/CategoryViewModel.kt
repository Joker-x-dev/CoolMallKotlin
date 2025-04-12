package com.joker.coolmall.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : ViewModel() {
    
    // 左侧分类数据
    private val _leftCategories = MutableStateFlow<List<Category>>(generateLeftCategories())
    val leftCategories: StateFlow<List<Category>> = _leftCategories
    
    // 右侧分类数据
    private val _rightCategories = MutableStateFlow<List<CategoryItem>>(emptyList())
    val rightCategories: StateFlow<List<CategoryItem>> = _rightCategories
    
    // 当前选中的左侧分类索引
    private val _selectedCategoryIndex = MutableStateFlow(0)
    val selectedCategoryIndex: StateFlow<Int> = _selectedCategoryIndex
    
    init {
        // 初始化时加载第一个分类的数据
        loadRightCategories(0)
    }
    
    // 选择分类并加载对应的右侧数据
    fun selectCategory(index: Int) {
        _selectedCategoryIndex.value = index
        loadRightCategories(index)
    }
    
    // 加载右侧数据
    private fun loadRightCategories(index: Int) {
        _rightCategories.value = getRightCategoriesForIndex(index)
    }
    
    // 生成左侧分类数据
    private fun generateLeftCategories(): List<Category> {
        return listOf(
            Category("网络"),
            Category("包包"),
            Category("会员"),
            Category("潮鞋"),
            Category("户外"),
            Category("家电"),
            Category("优惠"),
            Category("电脑"),
            Category("服装"),
            Category("手机")
        )
    }
    
    // 根据索引获取右侧分类数据
    private fun getRightCategoriesForIndex(index: Int): List<CategoryItem> {
        // 这里可以根据不同的索引返回不同的数据，现在模拟一些数据
        // 创建一些子分类项，这里简单复用不同的名称作为示例
        val leftName = generateLeftCategories()[index].name
        
        return listOf(
            CategoryItem(
                title = leftName,
                items = listOf(
                    SubCategoryItem("体重秤", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"),
                    SubCategoryItem("跑步机", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"),
                    SubCategoryItem("车模", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png")
                )
            ),
            CategoryItem(
                title = "电脑",
                items = listOf(
                    SubCategoryItem("键盘", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"),
                    SubCategoryItem("Redmi 轻薄本", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"),
                    SubCategoryItem("设计创作", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png")
                )
            ),
            CategoryItem(
                title = "服装",
                items = listOf(
                    SubCategoryItem("帽子", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"),
                    SubCategoryItem("T 恤", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"),
                    SubCategoryItem("卫衣", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png")
                )
            ),
            CategoryItem(
                title = "手机",
                items = listOf(
                    SubCategoryItem("红米 K 系列", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"),
                    SubCategoryItem("小米数字旗舰", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"),
                    SubCategoryItem("红米数字系列", "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png")
                )
            )
        )
    }
}

// 分类数据模型
data class Category(val name: String)

// 右侧分类项
data class CategoryItem(
    val title: String,
    val items: List<SubCategoryItem>
)

// 子分类项
data class SubCategoryItem(
    val title: String,
    val imageUrl: String
)
