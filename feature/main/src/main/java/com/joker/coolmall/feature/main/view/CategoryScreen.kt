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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.AppColumn
import com.joker.coolmall.core.designsystem.component.AppLazyColumn
import com.joker.coolmall.core.designsystem.component.AppRow
import com.joker.coolmall.core.designsystem.component.FullScreenBox
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.RadiusLarge
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.model.entity.CategoryTree
import com.joker.coolmall.core.model.preview.previewCategoryTreeList
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.state.CategoryUiState
import com.joker.coolmall.feature.main.viewmodel.CategoryViewModel
import kotlinx.coroutines.launch

/**
 * 分类页面路由
 *
 * @param viewModel 分类页面的ViewModel
 * @author Joker.X
 */
@Composable
internal fun CategoryRoute(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    // 分类UI状态
    val uiState by viewModel.categoryUiState.collectAsState()
    // 当前选中的分类索引
    val selectedIndex by viewModel.selectedCategoryIndex.collectAsState()

    CategoryScreen(
        uiState = uiState,
        selectedIndex = selectedIndex,
        onCategorySelected = viewModel::selectCategory,
        toGoodsCategory = viewModel::toGoodsCategoryPage,
        onRetry = viewModel::retryRequest
    )
}

/**
 * 分类页面内容
 *
 * @param uiState 当前UI状态
 * @param selectedIndex 当前选中的分类索引
 * @param onCategorySelected 分类选中回调
 * @param toGoodsCategory 跳转到商品分类页面
 * @param onRetry 重试加载数据回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryScreen(
    uiState: CategoryUiState = CategoryUiState.Loading,
    selectedIndex: Int = 0,
    onCategorySelected: (Int) -> Unit = {},
    toGoodsCategory: (Long) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    CommonScaffold(
        topBar = {
            CenterTopAppBar(R.string.category, showBackIcon = false)
        }
    ) { paddingValues ->
        FullScreenBox(padding = paddingValues) {
            when (uiState) {
                is CategoryUiState.Loading -> PageLoading()
                is CategoryUiState.Error -> EmptyNetwork(onRetryClick = onRetry)
                is CategoryUiState.Success -> CategoryContentView(
                    categoryTrees = uiState.data,
                    selectedIndex = selectedIndex,
                    onCategorySelected = onCategorySelected,
                    toGoodsCategory = toGoodsCategory
                )
            }
        }
    }
}

/**
 * 分类页面的主要内容 - 成功状态
 *
 * @param categoryTrees 分类树数据列表
 * @param selectedIndex 当前选中的分类索引
 * @param onCategorySelected 分类选中回调
 * @param toGoodsCategory 跳转到商品分类页面
 * @author Joker.X
 */
@Composable
private fun CategoryContentView(
    categoryTrees: List<CategoryTree>,
    selectedIndex: Int,
    onCategorySelected: (Int) -> Unit,
    toGoodsCategory: (Long) -> Unit
) {
    // 记住协程作用域，用于滚动操作
    val coroutineScope = rememberCoroutineScope()

    // 右侧列表的滚动状态
    val rightListState = rememberLazyListState()

    // 用于跟踪滚动方向，避免循环联动
    var isScrollingFromLeftClick by remember { mutableStateOf(false) }

    // 上次更新左侧选中项的索引
    var lastSelectedIndex by remember { mutableIntStateOf(selectedIndex) }

    // 使用derivedStateOf获取当前可见的一级分类索引
    val currentVisibleIndex = remember {
        derivedStateOf {
            // 获取当前所有可见项
            val visibleItems = rightListState.layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) return@derivedStateOf selectedIndex

            // 第一个完全可见的项
            val firstCompletelyVisibleItemIndex = visibleItems
                .firstOrNull { it.offset >= 0 }?.index ?: rightListState.firstVisibleItemIndex

            // 计算屏幕中心位置
            val centerOffset = rightListState.layoutInfo.viewportEndOffset / 2

            // 找到中心位置显示的项
            val centerItem = visibleItems.firstOrNull { itemInfo ->
                val itemCenter = itemInfo.offset + (itemInfo.size / 2)
                (itemCenter > 0) && (itemCenter < centerOffset)
            }

            // 优先使用中心项，其次是第一个完全可见项，最后是第一个可见项
            centerItem?.index ?: firstCompletelyVisibleItemIndex
        }
    }

    // 右侧滚动监听
    LaunchedEffect(currentVisibleIndex.value) {
        if (!isScrollingFromLeftClick && currentVisibleIndex.value != lastSelectedIndex) {
            // 当右侧滚动时，索引变化，且不是由左侧点击触发的，更新左侧选中项
            lastSelectedIndex = currentVisibleIndex.value
            onCategorySelected(currentVisibleIndex.value)
        }
    }

    // 当左侧选中项改变时，滚动右侧列表
    LaunchedEffect(selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < categoryTrees.size &&
            selectedIndex != lastSelectedIndex
        ) { // 避免重复滚动
            isScrollingFromLeftClick = true
            lastSelectedIndex = selectedIndex
            coroutineScope.launch {
                rightListState.animateScrollToItem(selectedIndex)
                // 滚动完成后重置标志
                isScrollingFromLeftClick = false
            }
        }
    }

    AppRow(
        modifier = Modifier.fillMaxSize()
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
            toGoodsCategory = toGoodsCategory,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
    }
}

/**
 * 左侧分类列表
 *
 * @param categories 分类名称列表
 * @param selectedIndex 当前选中的分类索引
 * @param onCategorySelected 分类选中回调
 * @param modifier 修饰符
 * @author Joker.X
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
                    isPrevious = index == selectedIndex - 1,
                    isNext = index == selectedIndex + 1,
                    isFirst = index == 0,
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
 *
 * @param name 分类名称
 * @param isSelected 是否是当前选中项
 * @param isPrevious 是否为选中项的前一项
 * @param isNext 是否为选中项的后一项
 * @param isFirst 是否为第一项
 * @param onClick 点击事件回调
 * @author Joker.X
 */
@Composable
private fun LeftCategoryItem(
    name: String,
    isSelected: Boolean,
    isPrevious: Boolean = false,
    isNext: Boolean = false,
    isFirst: Boolean = false,
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
 *
 * @param isLastSelected 最后一项是否被选中
 * @author Joker.X
 */
@Composable
private fun BottomPlaceholderItem(
    isLastSelected: Boolean
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
 *
 * @param modifier 修饰符
 * @param categoryTrees 分类树列表
 * @param listState 列表滚动状态
 * @param toGoodsCategory 跳转到商品分类页面
 * @author Joker.X
 */
@Composable
private fun RightCategoryContent(
    modifier: Modifier = Modifier,
    categoryTrees: List<CategoryTree>,
    listState: LazyListState,
    toGoodsCategory: (Long) -> Unit,
) {
    AppLazyColumn(
        listState = listState,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp) // 增加项之间的间距
    ) {
        // 遍历所有一级分类
        items(categoryTrees.size) { index ->
            val category = categoryTrees[index]

            // 分类区域整体包装，确保每个分类区域有足够的高度触发滚动监听
            AppColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) // 底部额外增加间距
            ) {
                // 分类标题作为分隔符
                TitleWithLine(category.name)

                Spacer(modifier = Modifier.height(8.dp)) // 标题和内容之间的间距

                // 二级分类内容
                if (category.children.isNotEmpty()) {
                    CategorySection(
                        categoryTree = category,
                        toGoodsCategory = toGoodsCategory
                    )
                }
            }
        }
    }
}

/**
 * 分类部分 - 显示单个分类的二级分类
 *
 * @param modifier 修饰符
 * @param categoryTree 分类树数据
 * @param toGoodsCategory 跳转到商品分类页面
 * @author Joker.X
 */
@Composable
private fun CategorySection(
    modifier: Modifier = Modifier,
    categoryTree: CategoryTree,
    toGoodsCategory: (Long) -> Unit = {},
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
                        onClick = { toGoodsCategory(subCategory.id) },
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
 *
 * @param modifier 修饰符
 * @param name 分类名称
 * @param imageUrl 图片URL
 * @param onClick 点击事件回调
 * @author Joker.X
 */
@Composable
private fun SubCategoryItem(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    onClick: () -> Unit = {}
) {
    AppColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
            .clip(ShapeSmall)
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

/**
 * 分类界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    AppTheme {
        CategoryScreen(
            uiState = CategoryUiState.Success(previewCategoryTreeList),
            selectedIndex = 1,
            onCategorySelected = { /* 预览时不执行任何操作 */ },
            toGoodsCategory = { /* 预览时不执行任何操作 */ }
        )
    }
} 