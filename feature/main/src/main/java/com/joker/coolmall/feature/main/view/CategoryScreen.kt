package com.joker.coolmall.feature.main.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.AppColumn
import com.joker.coolmall.core.designsystem.component.AppLazyColumn
import com.joker.coolmall.core.designsystem.component.AppRow
import com.joker.coolmall.core.designsystem.theme.RadiusLarge
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.model.CategoryTree
import com.joker.coolmall.feature.main.state.CategoryUiState
import com.joker.coolmall.feature.main.viewmodel.CategoryViewModel
import kotlinx.coroutines.launch

/**
 * 分类页面路由
 */
@Composable
internal fun CategoryRoute(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedIndex by viewModel.selectedCategoryIndex.collectAsState()

    when (uiState) {
        is CategoryUiState.Loading -> {
            // 显示加载状态
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "加载中...",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        is CategoryUiState.Error -> {
            // 显示错误状态
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "加载失败，请重试",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        is CategoryUiState.Success -> {
            val categoryTrees = (uiState as CategoryUiState.Success).data
            CategoryScreen(
                categoryTrees = categoryTrees,
                selectedIndex = selectedIndex,
                onCategorySelected = viewModel::selectCategory,
                onSubCategoryClick = viewModel::navigateToGoodsList
            )
        }
    }
}

/**
 * 分类页面内容
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryScreen(
    categoryTrees: List<CategoryTree> = emptyList(),
    selectedIndex: Int = 0,
    onCategorySelected: (Int) -> Unit = {},
    onSubCategoryClick: (Long) -> Unit = {}
) {
    // 记住协程作用域，用于滚动操作
    val coroutineScope = rememberCoroutineScope()
    
    // 右侧列表的滚动状态
    val rightListState = rememberLazyListState()
    
    // 用于跟踪滚动方向，避免循环联动
    var isScrollingFromLeftClick by remember { mutableStateOf(false) }
    
    // 右侧滚动监听
    LaunchedEffect(remember { derivedStateOf { rightListState.firstVisibleItemIndex } }) {
        if (!isScrollingFromLeftClick && rightListState.firstVisibleItemIndex >= 0) {
            // 当右侧滚动时，且不是由左侧点击触发的，更新左侧选中项
            onCategorySelected(rightListState.firstVisibleItemIndex)
        }
    }
    
    // 当左侧选中项改变时，滚动右侧列表
    LaunchedEffect(selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < categoryTrees.size) {
            isScrollingFromLeftClick = true
            coroutineScope.launch {
                rightListState.animateScrollToItem(selectedIndex)
                // 滚动完成后重置标志
                isScrollingFromLeftClick = false
            }
        }
    }

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
                // 提取一级分类名称用于左侧显示
                val rootCategories = categoryTrees.map { it.name }
                
                // 左侧分类列表
                LeftCategoryList(
                    categories = rootCategories,
                    selectedIndex = selectedIndex,
                    onCategorySelected = onCategorySelected,
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                )

                // 右侧内容区域 - 显示所有二级分类
                RightCategoryContent(
                    categoryTrees = categoryTrees,
                    listState = rightListState,
                    onSubCategoryClick = onSubCategoryClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
    )
}

/**
 * 左侧分类列表
 */
@Composable
private fun LeftCategoryList(
    categories: List<String>,
    selectedIndex: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        // 实际的分类列表
        AppLazyColumn {
            // 首先渲染所有实际分类项
            itemsIndexed(categories) { index, category ->
                LeftCategoryItem(
                    name = category,
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

/**
 * 左侧分类菜单项
 */
@Composable
private fun LeftCategoryItem(
    name: String,
    isSelected: Boolean,
    isPrevious: Boolean = false, // 是选中项的前一项
    isNext: Boolean = false,     // 是选中项的后一项
    isFirst: Boolean = false,    // 是否为第一项
    onClick: () -> Unit
) {


    // 确定圆角形状
    val cornerShape = if (!isSelected) {
        when {
            // 第一项 - 始终有右上角圆角
            isFirst -> {
                if (isPrevious) {
                    // 如果既是第一项又是前一项，同时有右上角和右下角圆角
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = RadiusLarge,
                        bottomEnd = RadiusLarge,
                        bottomStart = 0.dp
                    )
                } else {
                    // 仅是第一项，只有右上角圆角
                    RightTopRoundedShape
                }
            }

            isPrevious -> RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomEnd = RadiusLarge,
                bottomStart = 0.dp
            )  // 前一项右下角圆角
            isNext -> RightTopRoundedShape         // 后一项右上角圆角
            else -> RoundedCornerShape(0.dp)       // 其他项无圆角
        }
    } else {
        // 选中项没有圆角
        RoundedCornerShape(0.dp)
    }

    // 添加文本颜色动画
    val textColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(durationMillis = 300),
        label = "textColorAnimation"
    )

    // 添加指示条宽度动画
    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) 3.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "indicatorWidthAnimation"
    )

    // 添加字体大小动画
    val fontSize by animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1.0f, // 选中时字体放大20%
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "fontSizeAnimation"
    )

    // 添加字体粗细动画 (通过改变字重来实现)
    val fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal

    // 底层白色Box
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.surface)  // 使用surface颜色
            .then(
                if (!isSelected) {
                    // 非选中项可点击，带水波纹效果
                    Modifier
                        .clip(cornerShape) // 先裁剪确保水波纹有圆角
                        .clickable(onClick = onClick) // 使用默认的clickable带水波纹
                } else {
                    // 选中项不可点击
                    Modifier
                }
            )
    ) {
        // 如果不是选中项，则添加背景顶层Box（可能带圆角）
        if (!isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(cornerShape)
                    .background(MaterialTheme.colorScheme.background)  // 使用background颜色
            )
        }

        // 左侧指示条，使用动画宽度
        if (indicatorWidth > 0.dp) {
            Spacer(
                modifier = Modifier
                    .width(indicatorWidth)
                    .height(24.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.CenterStart)
            )
        }

        // 文本内容（最上层），使用动画颜色和大小
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = fontWeight
            ),
            color = textColor,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize * fontSize,
            textAlign = TextAlign.Center,  // 确保文本居中
            modifier = Modifier.fillMaxWidth()  // 填充整个宽度以确保居中
        )
    }
}

/**
 * 底部占位项
 */
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
            // 添加表面颜色背景
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            )

            // 顶层添加背景色圆角
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RightTopRoundedShape)
                    .background(MaterialTheme.colorScheme.background)
            )
        } else {
            // 否则就是普通的背景颜色
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }
}

/**
 * 右侧内容区域 - 显示所有二级分类
 */
@Composable
private fun RightCategoryContent(
    categoryTrees: List<CategoryTree>,
    listState: LazyListState,
    onSubCategoryClick: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    AppLazyColumn(
        listState = listState,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // 遍历所有一级分类
        items(categoryTrees.size) { index ->
            val category = categoryTrees[index]
            
            SpaceVerticalMedium()

            // 分类标题作为分隔符
            TitleWithLine(category.name)

            // 二级分类内容
            if (category.children.isNotEmpty()) {
                CategorySection(
                    categoryTree = category,
                    onSubCategoryClick = onSubCategoryClick
                )
            }
        }
    }
}

/**
 * 分类部分 - 显示单个分类的二级分类
 */
@Composable
private fun CategorySection(
    categoryTree: CategoryTree,
    onSubCategoryClick: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    AppColumn(modifier = modifier.fillMaxWidth()) {
        // 子分类网格
        val itemsPerRow = 3
        val chunkedItems = categoryTree.children.chunked(itemsPerRow)

        chunkedItems.forEach { rowItems ->
            AppRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { subCategory ->
                    SubCategoryItem(
                        name = subCategory.name,
                        imageUrl = subCategory.pic ?: "",
                        onClick = { onSubCategoryClick(subCategory.id) },
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

/**
 * 子分类项
 */
@Composable
private fun SubCategoryItem(
    name: String,
    imageUrl: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    AppColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        // 使用NetWorkImage替代Image
        NetWorkImage(
            model = imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(ShapeMedium)
        )

        // 标题
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// 右上角圆角
private val RightTopRoundedShape = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = RadiusLarge,
    bottomEnd = 0.dp,
    bottomStart = 0.dp
)

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    // 创建预览用的分类树
    val categoryTrees = listOf(
        CategoryTree(
            id = 1L,
            name = "手机",
            parentId = null,
            sortNum = 0,
            pic = null,
            status = 1,
            createTime = null,
            updateTime = null,
            children = listOf(
                CategoryTree(
                    id = 11L,
                    name = "小米手机",
                    parentId = 1,
                    sortNum = 0,
                    pic = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png",
                    status = 1,
                    createTime = null,
                    updateTime = null
                ),
                CategoryTree(
                    id = 12L,
                    name = "Redmi手机",
                    parentId = 1,
                    sortNum = 0,
                    pic = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png",
                    status = 1,
                    createTime = null,
                    updateTime = null
                )
            )
        ),
        CategoryTree(
            id = 2L,
            name = "电脑",
            parentId = null,
            sortNum = 0,
            pic = null,
            status = 1,
            createTime = null,
            updateTime = null,
            children = listOf(
                CategoryTree(
                    id = 21L,
                    name = "笔记本",
                    parentId = 2,
                    sortNum = 0,
                    pic = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png",
                    status = 1,
                    createTime = null,
                    updateTime = null
                ),
                CategoryTree(
                    id = 22L,
                    name = "台式机",
                    parentId = 2,
                    sortNum = 0,
                    pic = "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/ef2bf8a1400af4698a3e61fc7f4e340e.png",
                    status = 1,
                    createTime = null,
                    updateTime = null
                )
            )
        )
    )

    CategoryScreen(
        categoryTrees = categoryTrees,
        selectedIndex = 1,
        onCategorySelected = { /* 预览时不执行任何操作 */ },
        onSubCategoryClick = { /* 预览时不执行任何操作 */ }
    )
} 