package com.joker.coolmall.feature.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.AppColumn
import com.joker.coolmall.core.designsystem.component.AppLazyColumn
import com.joker.coolmall.core.designsystem.component.AppRow
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.viewmodel.Category
import com.joker.coolmall.feature.main.viewmodel.CategoryItem
import com.joker.coolmall.feature.main.viewmodel.CategoryViewModel
import com.joker.coolmall.feature.main.viewmodel.SubCategoryItem

@Composable
internal fun CategoryRoute(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val leftCategories by viewModel.leftCategories.collectAsState()
    val rightCategories by viewModel.rightCategories.collectAsState()
    val selectedIndex by viewModel.selectedCategoryIndex.collectAsState()

    CategoryScreen(
        leftCategories = leftCategories,
        rightCategories = rightCategories,
        selectedIndex = selectedIndex,
        onCategorySelected = viewModel::selectCategory
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryScreen(
    leftCategories: List<Category> = emptyList(),
    rightCategories: List<CategoryItem> = emptyList(),
    selectedIndex: Int = 0,
    onCategorySelected: (Int) -> Unit = {}
) {
    CommonScaffold(
        topBar = {
            CenterTopAppBar(R.string.category, showBackIcon = false)
        },
        content = { paddingValues ->
            AppRow(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // 左侧分类列表
                LeftCategoryList(
                    categories = leftCategories,
                    selectedIndex = selectedIndex,
                    onCategorySelected = onCategorySelected,
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                )
                
                // 右侧内容区域 - 移除了分隔线
                RightCategoryContent(
                    categories = rightCategories,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    )
}

@Composable
private fun LeftCategoryList(
    categories: List<Category>,
    selectedIndex: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(Color(0xFFF5F5F5))) {
        // 绘制选中项之前的圆角（上一个item的右下角圆角）
        if (selectedIndex > 0) {
            Spacer(
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 0.dp,
                            bottomEnd = 8.dp,
                            topStart = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .background(Color(0xFFF5F5F5))
                    .align(Alignment.TopEnd)
                    .padding(top = 50.dp * selectedIndex)
            )
        }
        
        // 绘制选中项之后的圆角（下一个item的右上角圆角）
        if (selectedIndex < categories.size - 1) {
            Spacer(
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 8.dp,
                            bottomEnd = 0.dp,
                            topStart = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .background(Color(0xFFF5F5F5))
                    .align(Alignment.TopEnd)
                    .padding(top = 50.dp * (selectedIndex + 1))
            )
        }
        
        // 实际的分类列表
        AppLazyColumn {
            itemsIndexed(categories) { index, category ->
                LeftCategoryItem(
                    name = category.name,
                    isSelected = index == selectedIndex,
                    onClick = { onCategorySelected(index) }
                )
            }
        }
    }
}

@Composable
private fun LeftCategoryItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable(onClick = onClick)
    ) {
        // 如果选中，在左侧添加主题色指示条
        if (isSelected) {
            // 添加左侧蓝色条
            Spacer(
                modifier = Modifier
                    .width(3.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.CenterStart)
            )
        }
        
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}

@Composable
private fun RightCategoryContent(
    categories: List<CategoryItem>,
    modifier: Modifier = Modifier
) {
    AppLazyColumn(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // 所有分类都使用分隔标题
        items(categories.size) { index ->
            val category = categories[index]
            
            // 分类标题作为分隔符
            CategoryDividerWithTitle(title = category.title)
            
            // 分类内容
            CategorySection(category = category, showTitle = false)
        }
    }
}

@Composable
private fun CategoryDividerWithTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Divider(
            color = Color.LightGray.copy(alpha = 0.5f),
            modifier = Modifier.align(Alignment.Center)
        )
        
        // 分类标题位于分隔线中央
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 8.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun CategorySection(
    category: CategoryItem,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true
) {
    AppColumn(modifier = modifier.fillMaxWidth()) {
        // 分类标题（如果需要显示）
        if (showTitle) {
            Text(
                text = category.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
        
        // 子分类网格
        val itemsPerRow = 3
        val chunkedItems = category.items.chunked(itemsPerRow)
        
        chunkedItems.forEach { rowItems ->
            AppRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { item ->
                    SubCategoryItem(
                        item = item,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // 如果一行不满itemsPerRow个，添加空白占位
                if (rowItems.size < itemsPerRow) {
                    Spacer(modifier = Modifier.weight(itemsPerRow - rowItems.size.toFloat()))
                }
            }
        }
    }
}

@Composable
private fun SubCategoryItem(
    item: SubCategoryItem,
    modifier: Modifier = Modifier
) {
    AppColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {
        // 使用NetWorkImage替代Image
        NetWorkImage(
            model = item.imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(4.dp))
        )
        
        // 标题
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    val leftCategories = listOf(
        Category("网络"),
        Category("包包"),
        Category("会员"),
        Category("潮鞋"),
        Category("户外")
    )
    
    val rightCategories = listOf(
        CategoryItem(
            title = "网络",
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
        )
    )
    
    CategoryScreen(
        leftCategories = leftCategories,
        rightCategories = rightCategories,
        selectedIndex = 0
    )
} 