package com.joker.coolmall.feature.main.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.preview.previewCartList
import com.joker.coolmall.core.ui.component.appbar.CenterTopAppBar
import com.joker.coolmall.core.ui.component.button.AppButtonFixed
import com.joker.coolmall.core.ui.component.button.ButtonShape
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonType
import com.joker.coolmall.core.ui.component.empty.EmptyCart
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.feature.main.R
import com.joker.coolmall.feature.main.component.CommonScaffold
import com.joker.coolmall.feature.main.viewmodel.CartViewModel

/**
 * 购物车页面路由
 *
 * @param viewModel 购物车ViewModel
 */
@Composable
internal fun CartRoute(
    viewModel: CartViewModel = hiltViewModel(),
) {
    val carts by viewModel.cartItems.collectAsState()
    val isEmpty by viewModel.isEmpty.collectAsState()
    val isEditing by viewModel.isEditing.collectAsState()
    val isAllSelected by viewModel.isAllSelected.collectAsState()
    val selectedCount by viewModel.selectedCount.collectAsState()
    val selectedTotalAmount by viewModel.selectedTotalAmount.collectAsState()
    val selectedItems by viewModel.selectedItems.collectAsState()

    CartScreen(
        carts = carts,
        isEmpty = isEmpty,
        isEditing = isEditing,
        isAllSelected = isAllSelected,
        selectedCount = selectedCount,
        selectedTotalAmount = selectedTotalAmount,
        selectedItems = selectedItems,
        onToggleEditMode = viewModel::toggleEditMode,
        onToggleSelectAll = viewModel::toggleSelectAll,
        onToggleItemSelection = viewModel::toggleItemSelection,
        onUpdateCartItemCount = viewModel::updateCartItemCount,
        onDeleteSelected = viewModel::deleteSelectedItems,
        onSettleClick = viewModel::onCheckoutClick
    )
}

/**
 * 购物车页面内容
 *
 * @param carts 购物车商品列表
 * @param isEmpty 购物车是否为空
 * @param isEditing 是否处于编辑模式
 * @param isAllSelected 是否全选状态
 * @param selectedCount 已选商品数量
 * @param selectedTotalAmount 已选商品总金额
 * @param selectedItems 已选商品和规格ID的映射
 * @param onToggleEditMode 切换编辑模式回调
 * @param onToggleSelectAll 切换全选状态回调
 * @param onToggleItemSelection 切换商品选中状态回调
 * @param onUpdateCartItemCount 更新商品数量回调
 * @param onDeleteSelected 删除已选商品回调
 * @param onSettleClick 结算按钮点击回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CartScreen(
    carts: List<Cart>,
    isEmpty: Boolean,
    isEditing: Boolean,
    isAllSelected: Boolean,
    selectedCount: Int,
    selectedTotalAmount: Int,
    selectedItems: Map<Long, Set<Long>>,
    onToggleEditMode: () -> Unit,
    onToggleSelectAll: () -> Unit,
    onToggleItemSelection: (Long, Long) -> Unit,
    onUpdateCartItemCount: (Long, Long, Int) -> Unit,
    onDeleteSelected: () -> Unit,
    onSettleClick: () -> Unit
) {
    CommonScaffold(
        topBar = {
            CenterTopAppBar(
                title = R.string.cart,
                showBackIcon = false,
                actions = {
                    TextButton(onClick = onToggleEditMode) {
                        Text(
                            text = stringResource(id = if (isEditing) R.string.complete else R.string.edit),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = {
            // 底部结算栏
            if (!isEmpty) {
                CartBottomBar(
                    isCheckAll = isAllSelected,
                    isEditing = isEditing,
                    selectedCount = selectedCount,
                    totalPrice = selectedTotalAmount,
                    onCheckAllChanged = onToggleSelectAll,
                    onDeleteClick = onDeleteSelected,
                    onSettleClick = onSettleClick,
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isEmpty) {
                EmptyCart()
            } else {
                // 购物车商品列表
                LazyColumn(
                    contentPadding = PaddingValues(SpacePaddingMedium),
                    verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        items = carts,
                        key = { "cart-${it.goodsId}" },
                    ) { cart ->
                        OrderGoodsCard(
                            data = cart,
                            onGoodsClick = { /* 实现商品点击事件 */ },
                            onSpecClick = { /* 规格点击事件 */ },
                            onQuantityChanged = { specId, newCount ->
                                onUpdateCartItemCount(cart.goodsId, specId, newCount)
                            },
                            itemSelectSlot = { spec ->
                                // 每次渲染时都会重新计算选中状态
                                val selected =
                                    selectedItems[cart.goodsId]?.contains(spec.id) == true
                                CartCheckButton(
                                    selected = selected,
                                    onClick = { onToggleItemSelection(cart.goodsId, spec.id) }
                                )
                            }
                        )
                    }
                }


            }
        }
    }
}

/**
 * 购物车底部结算栏
 *
 * @param isCheckAll 是否全选
 * @param isEditing 是否处于编辑模式
 * @param selectedCount 已选商品数量
 * @param totalPrice 已选商品总金额
 * @param onCheckAllChanged 切换全选状态回调
 * @param onDeleteClick 删除按钮点击回调
 * @param onSettleClick 结算按钮点击回调
 * @param modifier 修饰符
 */
@Composable
private fun CartBottomBar(
    isCheckAll: Boolean,
    isEditing: Boolean,
    selectedCount: Int,
    totalPrice: Int,
    onCheckAllChanged: () -> Unit,
    onDeleteClick: () -> Unit,
    onSettleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium)
        ) {
            CartCheckButton(
                selected = isCheckAll,
                onClick = onCheckAllChanged
            )

            SpaceHorizontalSmall()

            Text(text = stringResource(id = R.string.select_all))

            Spacer(modifier = Modifier.weight(1f))

            if (!isEditing) {
                Text(text = stringResource(id = R.string.total))
                SpaceHorizontalSmall()
                PriceText(totalPrice)
                SpaceHorizontalSmall()
                AppButtonFixed(
                    text = if (selectedCount == 0) stringResource(id = R.string.settle_account) else stringResource(
                        id = R.string.settle_account_count,
                        selectedCount
                    ),
                    onClick = onSettleClick,
                    enabled = selectedCount > 0,
                    size = ButtonSize.MINI,
                    type = ButtonType.DEFAULT,
                    shape = ButtonShape.ROUND,
                    modifier = Modifier
                        .widthIn(min = 90.dp)
                )
            } else {
                AppButtonFixed(
                    text = if (selectedCount == 0) stringResource(id = R.string.delete) else stringResource(
                        id = R.string.delete_count,
                        selectedCount
                    ),
                    onClick = onDeleteClick,
                    enabled = selectedCount > 0,
                    size = ButtonSize.MINI,
                    type = ButtonType.DANGER,
                    shape = ButtonShape.ROUND,
                    modifier = Modifier
                        .widthIn(min = 90.dp)
                )
            }
        }
    }
}

/**
 * 购物车选择按钮组件
 *
 * @param selected 是否选中
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param size 按钮大小
 * @param shape 按钮形状
 */
@Composable
private fun CartCheckButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Int = 24,
    shape: Shape = ShapeCircle
) {
    CommonIcon(
        resId = if (selected) R.drawable.ic_success_circle else R.drawable.ic_circle,
        contentDescription = if (selected) "已选择" else "未选择",
        modifier = modifier
            .size(size.dp)
            .clip(shape)
            .clickable(onClick = onClick),
        tint = if (selected) Primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(
            alpha = 0.6f
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    AppTheme {
        CartScreen(
            carts = previewCartList,
            isEmpty = false,
            isEditing = false,
            isAllSelected = false,
            selectedCount = 2,
            selectedTotalAmount = 12997,
            selectedItems = mapOf(),
            onToggleEditMode = { },
            onToggleSelectAll = { },
            onToggleItemSelection = { _, _ -> },
            onUpdateCartItemCount = { _, _, _ -> },
            onDeleteSelected = { },
            onSettleClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyCartScreenPreview() {
    AppTheme {
        CartScreen(
            carts = emptyList(),
            isEmpty = true,
            isEditing = false,
            isAllSelected = false,
            selectedCount = 0,
            selectedTotalAmount = 0,
            selectedItems = mapOf(),
            onToggleEditMode = { },
            onToggleSelectAll = { },
            onToggleItemSelection = { _, _ -> },
            onUpdateCartItemCount = { _, _, _ -> },
            onDeleteSelected = { },
            onSettleClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenEditingPreview() {
    AppTheme {
        CartScreen(
            carts = previewCartList,
            isEmpty = false,
            isEditing = true,
            isAllSelected = true,
            selectedCount = 2,
            selectedTotalAmount = 12997,
            selectedItems = mapOf(1L to setOf(1L)),
            onToggleEditMode = { },
            onToggleSelectAll = { },
            onToggleItemSelection = { _, _ -> },
            onUpdateCartItemCount = { _, _, _ -> },
            onDeleteSelected = { },
            onSettleClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenDarkPreview() {
    AppTheme(darkTheme = true) {
        CartScreen(
            carts = previewCartList,
            isEmpty = false,
            isEditing = false,
            isAllSelected = false,
            selectedCount = 2,
            selectedTotalAmount = 12997,
            selectedItems = mapOf(),
            onToggleEditMode = { },
            onToggleSelectAll = { },
            onToggleItemSelection = { _, _ -> },
            onUpdateCartItemCount = { _, _, _ -> },
            onDeleteSelected = { },
            onSettleClick = { }
        )
    }
}