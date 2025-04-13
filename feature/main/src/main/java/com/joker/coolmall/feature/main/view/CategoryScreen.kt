package com.joker.coolmall.feature.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.AppColumn
import com.joker.coolmall.core.designsystem.component.AppLazyColumn
import com.joker.coolmall.core.designsystem.component.AppRow
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.title.TitleWithLine
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

                // 右侧内容区域
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
        // 实际的分类列表
        AppLazyColumn {
            // 首先渲染所有实际分类项
            itemsIndexed(categories) { index, category ->
                LeftCategoryItem(
                    name = category.name,
                    isSelected = index == selectedIndex,
                    isPrevious = index == selectedIndex - 1, // 是否为选中项的前一项
                    isNext = index == selectedIndex + 1,     // 是否为选中项的后一项
                    isFirst = index == 0,                    // 是否为第一项
                    onClick = { onCategorySelected(index) }
                )
            }
            
            // 额外添加一个不可点击的底部占位项（用于实现最后一项的圆角效果）
            item {
                BottomPlaceholderItem(
                    isLastSelected = selectedIndex == categories.size - 1
                )
            }
        }
    }
}

@Composable
private fun LeftCategoryItem(
    name: String,
    isSelected: Boolean,
    isPrevious: Boolean = false, // 是选中项的前一项
    isNext: Boolean = false,     // 是选中项的后一项
    isFirst: Boolean = false,    // 是否为第一项
    onClick: () -> Unit
) {
    // 底层白色Box
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White)  // 底层都是白色
            .clickable(onClick = onClick)
    ) {
        // 如果不是选中项，则添加灰色顶层Box（可能带圆角）
        if (!isSelected) {
            // 确定顶层灰色Box的圆角形状
            val cornerShape = when {
                // 第一项 - 始终有右上角圆角
                isFirst -> {
                    if (isPrevious) {
                        // 如果既是第一项又是前一项，同时有右上角和右下角圆角
                        RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                    } else {
                        // 仅是第一项，只有右上角圆角
                        RoundedCornerShape(topEnd = 16.dp)
                    }
                }
                isPrevious -> RoundedCornerShape(bottomEnd = 16.dp)  // 前一项右下角圆角
                isNext -> RoundedCornerShape(topEnd = 16.dp)         // 后一项右上角圆角
                else -> RoundedCornerShape(0.dp)                     // 其他项无圆角
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(cornerShape)
                    .background(Color(0xFFF5F5F5))  // 灰色覆盖层
            )
        }
        
        // 如果选中，在左侧添加主题色指示条（最上层）
        if (isSelected) {
            Spacer(
                modifier = Modifier
                    .width(3.dp)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.CenterStart)
            )
        }

        // 文本内容（最上层）
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}

@Composable
private fun BottomPlaceholderItem(
    isLastSelected: Boolean // 最后一项是否被选中
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        // 只有当最后一项被选中时，才需要添加右上角的圆角
        if (isLastSelected) {
            // 添加白色背景
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
            
            // 顶层添加灰色圆角
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topEnd = 16.dp))
                    .background(Color(0xFFF5F5F5))
            )
        } else {
            // 否则就是普通的灰色背景
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
            )
        }
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

            SpaceVerticalMedium()

            // 分类标题作为分隔符
            TitleWithLine(category.title)

            // 分类内容
            CategorySection(category = category, showTitle = false)
        }
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
                SubCategoryItem(
                    "体重秤",
                    "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"
                ),
                SubCategoryItem(
                    "跑步机",
                    "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"
                ),
                SubCategoryItem(
                    "车模",
                    "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"
                )
            )
        ),
        CategoryItem(
            title = "电脑",
            items = listOf(
                SubCategoryItem(
                    "键盘",
                    "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"
                ),
                SubCategoryItem(
                    "Redmi 轻薄本",
                    "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"
                ),
                SubCategoryItem(
                    "设计创作",
                    "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png"
                )
            )
        )
    )

    CategoryScreen(
        leftCategories = leftCategories,
        rightCategories = rightCategories,
        selectedIndex = 1
    )
} 