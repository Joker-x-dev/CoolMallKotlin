package com.joker.coolmall.feature.goods.component

import CategoryUiState
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.ShapeLarge
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingXSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.button.AppButtonBordered
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonStyle
import com.joker.coolmall.core.ui.component.button.ButtonType
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.goods.model.CategoryTree

/**
 * 筛选对话框
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedVisibilityScope 动画可见性作用域
 * @param onDismiss 关闭对话框回调
 * @param uiState 分类数据状态
 * @param selectedCategoryIds 已选中的分类ID列表
 * @param minPrice 最低价格
 * @param maxPrice 最高价格
 * @param onApplyFilters 应用筛选条件回调
 * @param onResetFilters 重置筛选条件回调
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilterDialog(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onDismiss: () -> Unit,
    uiState: CategoryUiState,
    selectedCategoryIds: List<Long> = emptyList(),
    minPrice: String = "",
    maxPrice: String = "",
    onApplyFilters: (categoryIds: List<Long>, minPrice: String, maxPrice: String) -> Unit = { _, _, _ -> },
    onResetFilters: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {},
    ) {
        // 背景遮罩
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    onDismiss()
                },
        )

        with(sharedTransitionScope) {
            Column(
                modifier = Modifier
                    .padding(SpacePaddingLarge)
                    .align(Alignment.Center)
                    .heightIn(max = 680.dp)
                    .clip(ShapeLarge)
                    .sharedBounds(
                        rememberSharedContentState("filter_button"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                        clipInOverlayDuringTransition = OverlayClip(ShapeLarge),
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) { /* 阻止点击穿透 */ },
            ) {
                AnimatedContent(
                    targetState = uiState,
                    transitionSpec = {
                        // 定义进入和退出动画
                        fadeIn(animationSpec = tween(300)) togetherWith
                                fadeOut(animationSpec = tween(300))
                    },
                    label = "NetworkStateAnimation"
                ) { state ->
                    when (state) {
                        is CategoryUiState.Loading -> PageLoading()
                        is CategoryUiState.Error -> EmptyNetwork()
                        is CategoryUiState.Success -> FilterContentView(
                            data = state.data,
                            onDismiss = onDismiss,
                            selectedCategoryIds = selectedCategoryIds,
                            minPrice = minPrice,
                            maxPrice = maxPrice,
                            onApplyFilters = onApplyFilters,
                            onResetFilters = onResetFilters,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterContentView(
    data: List<CategoryTree>,
    onDismiss: () -> Unit,
    selectedCategoryIds: List<Long> = emptyList(),
    minPrice: String = "",
    maxPrice: String = "",
    onApplyFilters: (categoryIds: List<Long>, minPrice: String, maxPrice: String) -> Unit = { _, _, _ -> },
    onResetFilters: () -> Unit = {},
) {
    var currentMinPrice by remember { mutableStateOf(minPrice) }
    var currentMaxPrice by remember { mutableStateOf(maxPrice) }
    var selectedCategories by remember { mutableStateOf(selectedCategoryIds.toSet()) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 标题栏
        SpaceBetweenRow(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(SpacePaddingMedium)
                .align(Alignment.TopCenter)
                .zIndex(1f)
        ) {
            AppText(
                text = "筛选",
                size = TextSize.TITLE_LARGE
            )

            Box(
                modifier = Modifier
                    .clip(ShapeSmall)
                    .clickable { onDismiss() }
                    .padding(SpacePaddingXSmall)
            ) {
                CommonIcon(
                    imageVector = Icons.Default.Close,
                    size = 24.dp,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        VerticalList(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 50.dp, top = 50.dp)
        ) {
            // 价格区间
            Card {
                Column(
                    modifier = Modifier.padding(SpacePaddingMedium)
                ) {
                    AppText(
                        text = "价格区间",
                        modifier = Modifier.padding(bottom = SpacePaddingMedium)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(SpacePaddingMedium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 最低价输入框
                        PriceInputField(
                            value = currentMinPrice,
                            onValueChange = { currentMinPrice = it },
                            placeholder = "最低价",
                            modifier = Modifier.weight(1f)
                        )

                        // 分隔线
                        Box(
                            modifier = Modifier
                                .width(16.dp)
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.outline)
                        )

                        // 最高价输入框
                        PriceInputField(
                            value = currentMaxPrice,
                            onValueChange = { currentMaxPrice = it },
                            placeholder = "最高价",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // 分类筛选
            data.forEach { category ->
                Card {
                    Column(
                        modifier = Modifier.padding(SpacePaddingMedium)
                    ) {
                        AppText(
                            text = category.name,
                            size = TextSize.BODY_LARGE,
                            modifier = Modifier.padding(bottom = SpacePaddingMedium)
                        )

                        if (category.children.isNotEmpty()) {
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                category.children.forEach { child ->
                                    CategoryChip(
                                        category = child,
                                        isSelected = selectedCategories.contains(child.id),
                                        onSelectionChanged = { isSelected ->
                                            selectedCategories = if (isSelected) {
                                                selectedCategories + child.id
                                            } else {
                                                selectedCategories - child.id
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 悬浮在底部的按钮
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shadowElevation = 4.dp,
        ) {
            Row(
                modifier = Modifier.padding(SpacePaddingSmall)
            ) {
                AppButtonBordered(
                    text = "重置",
                    onClick = {
                        currentMinPrice = ""
                        currentMaxPrice = ""
                        selectedCategories = emptySet()
                        onResetFilters()
                    },
                    type = ButtonType.DEFAULT,
                    size = ButtonSize.MINI,
                    modifier = Modifier.weight(1f)
                )

                SpaceHorizontalMedium()

                AppButton(
                    text = "确定",
                    onClick = {
                        onApplyFilters(
                            selectedCategories.toList(),
                            currentMinPrice,
                            currentMaxPrice
                        )
                    },
                    type = ButtonType.DEFAULT,
                    size = ButtonSize.MINI,
                    style = ButtonStyle.GRADIENT,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * 价格输入框
 *
 * @param value 当前输入值
 * @param onValueChange 值改变回调
 * @param placeholder 占位文本
 * @param modifier 额外的修饰符
 */
@Composable
private fun PriceInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = ShapeCircle
            )
            .clip(ShapeCircle)
            .padding(horizontal = SpaceHorizontalLarge, vertical = SpaceVerticalSmall)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    AppText(
                        text = placeholder,
                        size = TextSize.BODY_MEDIUM,
                        type = TextType.TERTIARY
                    )
                }
                innerTextField()
            }
        )
    }
}

/**
 * 分类选择标签
 *
 * @param category 分类数据
 * @param isSelected 是否选中
 * @param onSelectionChanged 选中状态改变回调
 */
@Composable
private fun CategoryChip(
    category: CategoryTree,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit
) {
    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = ShapeCircle
            )
            .clip(ShapeCircle)
            .background(backgroundColor)
            .clickable {
                onSelectionChanged(!isSelected)
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            NetWorkImage(
                model = category.pic,
                size = 18.dp
            )
            SpaceHorizontalXSmall()
            AppText(
                text = category.name,
                size = TextSize.BODY_MEDIUM,
                color = textColor
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun FilterDialogPreview() {
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

    AppTheme {
        FilterContentView(
            onDismiss = {},
            data = categoryTrees
        )
    }
}