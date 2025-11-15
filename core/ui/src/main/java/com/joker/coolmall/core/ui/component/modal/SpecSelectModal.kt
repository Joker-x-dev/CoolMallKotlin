package com.joker.coolmall.core.ui.component.modal

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.CenterColumn
import com.joker.coolmall.core.designsystem.component.SmallPaddingColumn
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.component.VerticalScroll
import com.joker.coolmall.core.designsystem.component.WrapColumn
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.Goods
import com.joker.coolmall.core.model.entity.GoodsSpec
import com.joker.coolmall.core.model.entity.SelectedGoods
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.button.AppButtonBordered
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonStyle
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.stepper.QuantityStepper
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine

/**
 * 规格选择底部弹出层
 *
 * @param visible 是否显示
 * @param onDismiss 关闭回调
 * @param goods 商品信息
 * @param uiState 规格状态
 * @param onSpecSelected 规格选择回调
 * @param onAddToCart 加入购物车按钮点击回调
 * @param onBuyNow 立即购买按钮点击回调
 * @param onRetry 重试回调
 * @param selectedSpec 当前选中的规格，由外部传入并保持状态
 * @param onExpanded 弹窗展开完成回调，用于在动画完成后加载数据
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecSelectModal(
    visible: Boolean,
    onDismiss: () -> Unit,
    goods: Goods? = null,
    uiState: BaseNetWorkUiState<List<GoodsSpec>> = BaseNetWorkUiState.Loading,
    onSpecSelected: (GoodsSpec) -> Unit = {},
    onAddToCart: (SelectedGoods) -> Unit = {},
    onBuyNow: (SelectedGoods) -> Unit = {},
    onRetry: () -> Unit = {},
    selectedSpec: GoodsSpec? = null, // 添加已选择的规格参数
    onExpanded: () -> Unit = {}, // 弹窗展开完成回调
) {
    // 记录当前选中的规格索引
    var selectedSpecIndex by remember { mutableIntStateOf(-1) }
    // 当前选择的规格 - 使用selectedSpec作为初始值
    var currentSpec by remember(selectedSpec) { mutableStateOf(selectedSpec) }
    // 选择的数量
    var quantity by remember { mutableIntStateOf(1) }
    // 规格展示模式 (true=网格模式，false=列表模式)
    var isGridMode by remember { mutableStateOf(true) }

    // 商品封面图，如果没有则使用默认图片
    val coverImage = goods?.mainPic ?: ""

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        // 确保初始展开
        confirmValueChange = { it != SheetValue.PartiallyExpanded }
    )

    // 确保弹窗在初始状态下展开到最大，并在展开完成后触发回调
    LaunchedEffect(visible) {
        if (visible && sheetState.currentValue != SheetValue.Expanded) {
            sheetState.expand()
        }
    }

    // 监听 sheetState 变化，当完全展开时触发回调
    LaunchedEffect(sheetState.currentValue) {
        if (sheetState.currentValue == SheetValue.Expanded && visible) {
            onExpanded()
        }
    }

    // 每次弹窗显示时，根据specs和selectedSpec更新selectedSpecIndex
    LaunchedEffect(visible, uiState) {
        if (visible && uiState is BaseNetWorkUiState.Success && selectedSpec != null) {
            // 查找当前选中规格的索引
            val specs = uiState.data
            val index = specs.indexOfFirst { it.id == selectedSpec.id }
            if (index >= 0) {
                selectedSpecIndex = index
                currentSpec = selectedSpec
            }
        }
    }

    BottomModal(
        visible = visible,
        onDismiss = onDismiss,
        title = stringResource(id = R.string.modal_select_spec),
        sheetState = sheetState
    ) {
        BaseNetWorkView(
            uiState = uiState,
            customLoading = {
                PageLoading(modifier = Modifier.height(300.dp))
            },
            customError = {
                EmptyNetwork(modifier = Modifier.height(300.dp), onRetryClick = onRetry)
            },
        ) { specs ->
            SpecSelectModalContentView(
                specs = specs,
                goods = goods,
                currentSpec = currentSpec,
                selectedSpecIndex = selectedSpecIndex,
                quantity = quantity,
                isGridMode = isGridMode,
                coverImage = coverImage,
                onSpecSelected = { index, spec ->
                    // 如果点击的是已选中的规格，则取消选择
                    if (index == selectedSpecIndex) {
                        selectedSpecIndex = -1
                        currentSpec = null
                        onSpecSelected(spec) // 通知外部已取消选择
                    } else {
                        selectedSpecIndex = index
                        currentSpec = spec
                        onSpecSelected(spec)
                    }
                },
                onQuantityChanged = { newQuantity ->
                    quantity = newQuantity
                },
                onViewModeChanged = { newMode ->
                    isGridMode = newMode
                },
                onAddToCart = { spec ->
                    // 创建Cart对象并传递给回调
                    val selectedGoods = SelectedGoods().apply {
                        goodsId = spec.goodsId
                        goodsInfo = goods
                        this.spec = spec
                        count = quantity
                    }
                    onAddToCart(selectedGoods)
                    onDismiss()
                },
                onBuyNow = { spec ->
                    // 创建Cart对象并传递给回调
                    val selectedGoods = SelectedGoods().apply {
                        goodsId = spec.goodsId
                        goodsInfo = goods
                        this.spec = spec
                        count = quantity
                    }
                    onBuyNow(selectedGoods)
                    onDismiss()
                }
            )
        }
    }
}

/**
 * 规格选择底部弹出层主容器视图
 *
 * 这个可组合函数封装了规格选择弹出层的所有UI内容，包括规格展示、数量选择和底部按钮等。
 * 通过将UI逻辑从SpecSelectModal中分离出来，提高了代码的可维护性和可测试性。
 * 注意：此组件只处理成功加载后的内容，网络状态处理由上层组件完成。
 *
 * @param specs 规格列表数据
 * @param goods 商品信息，可为空
 * @param currentSpec 当前选中的规格，可为空
 * @param selectedSpecIndex 当前选中的规格索引，默认为-1（未选中）
 * @param quantity 选择的商品数量，默认为1
 * @param isGridMode 是否使用网格视图模式展示规格，默认为true
 * @param coverImage 规格选择卡片上显示的封面图URL
 * @param onSpecSelected 规格选择回调，参数为选中的规格索引和规格对象
 * @param onQuantityChanged 数量变更回调，参数为新的数量值
 * @param onViewModeChanged 视图模式切换回调，参数为新的视图模式布尔值
 * @param onAddToCart 添加到购物车回调，参数为选中的规格对象
 * @param onBuyNow 立即购买回调，参数为选中的规格对象
 * @author Joker.X
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SpecSelectModalContentView(
    specs: List<GoodsSpec>,
    goods: Goods? = null,
    currentSpec: GoodsSpec? = null,
    selectedSpecIndex: Int = -1,
    quantity: Int = 1,
    isGridMode: Boolean = true,
    coverImage: String = "",
    onSpecSelected: (Int, GoodsSpec) -> Unit = { _, _ -> },
    onQuantityChanged: (Int) -> Unit = {},
    onViewModeChanged: (Boolean) -> Unit = {},
    onAddToCart: (GoodsSpec) -> Unit = {},
    onBuyNow: (GoodsSpec) -> Unit = {}
) {
    // 获取屏幕尺寸信息
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val screenHeight = with(density) { windowInfo.containerSize.height.toDp() }

    // 动态计算规格选择区域的最大高度
    // 考虑头部信息(约120dp)、标题和间距(约60dp)、数量选择器(约60dp)、底部按钮(约60dp)、安全边距(约80dp)
    val reservedHeight = 380.dp
    val maxSpecHeight = (screenHeight - reservedHeight).coerceAtLeast(200.dp)

    WrapColumn {
        // 商品基本信息
        goods?.let {
            SpecHeaderInfo(it, currentSpec)
            SpaceVerticalMedium()
            WeDivider()
        }

        SpaceVerticalMedium()

        SpaceBetweenRow {
            TitleWithLine(stringResource(id = R.string.spec_category))
            CommonIcon(
                resId = if (isGridMode) R.drawable.ic_menu_list else R.drawable.ic_menu,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier
                    .clickable(
                        onClick = { onViewModeChanged(!isGridMode) }
                    )
            )
        }

        SpaceVerticalMedium()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxSpecHeight)
        ) {
            VerticalScroll {
                AnimatedContent(
                    targetState = isGridMode,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(300, easing = LinearEasing)) +
                                scaleIn(
                                    initialScale = 0.92f,
                                    animationSpec = tween(300, easing = LinearEasing)
                                ))
                            .togetherWith(
                                fadeOut(animationSpec = tween(300, easing = LinearEasing)) +
                                        scaleOut(
                                            targetScale = 0.92f,
                                            animationSpec = tween(300, easing = LinearEasing)
                                        )
                            )
                    },
                    label = "规格视图切换动画"
                ) { targetIsGridMode ->
                    if (targetIsGridMode) {
                        GridSpecSelectionContent(
                            specs = specs,
                            selectedIndex = selectedSpecIndex,
                            onSpecSelected = onSpecSelected,
                            coverImage = coverImage
                        )
                    } else {
                        ListSpecSelectionContent(
                            specs = specs,
                            selectedIndex = selectedSpecIndex,
                            onSpecSelected = onSpecSelected,
                            coverImage = coverImage
                        )
                    }
                }
            }
        }

        SpaceVerticalLarge()

        // 数量选择 - 只有选中规格时才显示
        if (currentSpec != null) {
            QuantitySelector(
                quantity = quantity,
                onDecrement = { if (quantity > 1) onQuantityChanged(quantity - 1) },
                onIncrement = { onQuantityChanged(quantity + 1) }
            )

            SpaceVerticalLarge()
        }

        // 底部按钮
        SpecBottomButtons(
            selectedSpec = currentSpec,
            onAddToCart = onAddToCart,
            onBuyNow = onBuyNow
        )
    }
}

/**
 * 规格选择头部商品信息
 *
 * @param goods 商品数据
 * @param selectedSpec 选中的规格
 * @author Joker.X
 */
@Composable
private fun SpecHeaderInfo(
    goods: Goods,
    selectedSpec: GoodsSpec?
) {
    StartRow {
        // 使用选中规格的图片或商品主图
        val imageUrl = selectedSpec?.images?.firstOrNull() ?: goods.mainPic

        NetWorkImage(
            model = imageUrl,
            size = 100.dp,
            cornerShape = ShapeSmall,
            showBackground = true,
        )

        SpaceHorizontalMedium()

        Column {
            // 价格 - 选中规格时显示规格价格，否则显示商品价格
            /*AppText(
                text = "¥${selectedSpec?.price ?: goods.price}",
                size = TextSize.DISPLAY_LARGE,
                color = ColorDanger
            )*/

            PriceText(
                selectedSpec?.price ?: goods.price,
                integerTextSize = TextSize.DISPLAY_LARGE
            )

            SpaceVerticalSmall()

            // 选中规格显示"已选"，未选中显示"未选择"
            if (selectedSpec != null) {
                AppText(
                    text = stringResource(id = R.string.spec_selected) + "：${selectedSpec.name}",
                    type = TextType.SECONDARY,
                    size = TextSize.BODY_MEDIUM
                )
            } else {
                AppText(
                    text = stringResource(id = R.string.spec_not_selected),
                    type = TextType.SECONDARY,
                    size = TextSize.BODY_MEDIUM
                )
            }

            SpaceVerticalSmall()

            // 库存 - 选中规格时显示规格库存，否则显示0
            AppText(
                text = stringResource(id = R.string.spec_stock) + "：${selectedSpec?.stock ?: 0}",
                type = TextType.TERTIARY,
                size = TextSize.BODY_MEDIUM
            )
        }
    }
}

/**
 * 网格模式的规格选择内容
 *
 * @param specs 规格列表
 * @param selectedIndex 选中的索引
 * @param onSpecSelected 规格选择回调
 * @param coverImage 封面图
 * @author Joker.X
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GridSpecSelectionContent(
    specs: List<GoodsSpec>,
    selectedIndex: Int,
    onSpecSelected: (Int, GoodsSpec) -> Unit,
    coverImage: String
) {
    val itemsPerRow = 3

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SpaceVerticalSmall),
        verticalArrangement = Arrangement.spacedBy(SpaceVerticalSmall),
        maxItemsInEachRow = itemsPerRow
    ) {
        specs.forEachIndexed { index, spec ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 130.dp)
                    .clip(ShapeSmall)
                    .border(
                        width = 1.dp,
                        color = if (index == selectedIndex) Primary else MaterialTheme.colorScheme.outline.copy(
                            alpha = 0.8f
                        ),
                        shape = ShapeSmall
                    )
                    .background(
                        if (index == selectedIndex) Primary.copy(alpha = 0.05f) else Color.Transparent
                    )
                    .clickable { onSpecSelected(index, spec) }
            ) {
                CenterColumn {
                    // 优先使用规格的图片，如果没有再使用商品的封面图
                    val specImage = spec.images?.firstOrNull() ?: coverImage

                    NetWorkImage(
                        model = specImage,
                        size = 100.dp,
                        cornerShape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        showBackground = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .padding(horizontal = SpaceHorizontalSmall),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // 规格名称
                        AppText(
                            text = spec.name,
                            size = TextSize.BODY_MEDIUM,
                            color = if (index == selectedIndex) Primary else MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )

                        // 价格
                        /*AppText(
                            text = "¥${spec.price}",
                            size = TextSize.TITLE_LARGE,
                            color = ColorDanger,
                        )*/
                    }


                }
            }
        }

        // 添加隐形占位项以确保每行都有相同数量的元素
        val remainingItems = (itemsPerRow - (specs.size % itemsPerRow)) % itemsPerRow
        repeat(remainingItems) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 130.dp)
            )
        }
    }
}

/**
 * 列表模式的规格选择内容
 *
 * @param specs 规格列表
 * @param selectedIndex 选中的索引
 * @param onSpecSelected 规格选择回调
 * @param coverImage 封面图
 * @author Joker.X
 */
@Composable
private fun ListSpecSelectionContent(
    specs: List<GoodsSpec>,
    selectedIndex: Int,
    onSpecSelected: (Int, GoodsSpec) -> Unit,
    coverImage: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SpaceVerticalMedium)
    ) {
        specs.forEachIndexed { index, spec ->
            StartRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(ShapeSmall)
                    .border(
                        width = 1.dp,
                        color = if (index == selectedIndex) Primary else MaterialTheme.colorScheme.outline.copy(
                            alpha = 0.8f
                        ),
                        shape = ShapeSmall
                    )
                    .background(
                        if (index == selectedIndex) Primary.copy(alpha = 0.05f) else Color.Transparent
                    )
                    .clickable { onSpecSelected(index, spec) }
                    .padding(SpacePaddingSmall),
            ) {
                // 优先使用规格的图片，如果没有再使用商品的封面图
                val specImage = spec.images?.firstOrNull() ?: coverImage

                NetWorkImage(
                    model = specImage,
                    size = 50.dp,
                    cornerShape = ShapeSmall,
                    showBackground = true
                )

                SpaceHorizontalSmall()

                SmallPaddingColumn {
                    // 规格名称
                    AppText(
                        text = spec.name,
                        size = TextSize.BODY_MEDIUM,
                        color = if (index == selectedIndex) Primary else MaterialTheme.colorScheme.onSurface
                    )

                    SpaceVerticalXSmall()

                    // 价格
                    AppText(
                        text = "¥${spec.price}",
                        color = ColorDanger
                    )
                }
            }
        }
    }
}

/**
 * 数量选择器
 *
 * @param quantity 数量
 * @param onDecrement 减少回调
 * @param onIncrement 增加回调
 * @author Joker.X
 */
@Composable
private fun QuantitySelector(
    quantity: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    SpaceBetweenRow {
        TitleWithLine(stringResource(id = R.string.quantity))
        QuantityStepper(
            quantity = quantity,
            onQuantityChanged = { newQuantity ->
                if (newQuantity > quantity) {
                    onIncrement()
                } else if (newQuantity < quantity) {
                    onDecrement()
                }
            }
        )
    }
}

/**
 * 底部按钮
 *
 * @param selectedSpec 选中的规格
 * @param onAddToCart 加入购物车回调
 * @param onBuyNow 立即购买回调
 * @author Joker.X
 */
@Composable
private fun SpecBottomButtons(
    selectedSpec: GoodsSpec?,
    onAddToCart: (GoodsSpec) -> Unit,
    onBuyNow: (GoodsSpec) -> Unit
) {
    // 判断是否有库存
    val hasStock = (selectedSpec?.stock ?: 0) > 0
    val buttonEnabled = selectedSpec != null && hasStock

    Row {
        AppButtonBordered(
            text = stringResource(id = R.string.modal_add_to_cart),
            onClick = { selectedSpec?.let { onAddToCart(it) } },
            modifier = Modifier.weight(1f),
            enabled = buttonEnabled,
            size = ButtonSize.SMALL
        )

        SpaceHorizontalMedium()

        AppButton(
            text = stringResource(id = R.string.modal_buy_now),
            onClick = { selectedSpec?.let { onBuyNow(it) } },
            modifier = Modifier.weight(1f),
            enabled = buttonEnabled,
            size = ButtonSize.SMALL,
            style = ButtonStyle.GRADIENT
        )
    }
}

/**
 * 规格选择底部弹出层预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun SpecSelectModalPreview() {
    AppTheme {
        SpecSelectModalContentView(
            goods = Goods(
                id = 1,
                createTime = "2025-03-29 00:17:15",
                updateTime = "2025-03-29 23:07:48",
                typeId = 11,
                title = "Redmi 14C",
                subTitle = "【持久续航】5160mAh 大电池",
                mainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                pics = listOf(
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F32051f923ded432c82ef5934451a601b_b2.jpg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F88bf37e8c9ce42968067cbf3d717f613_b3.jpg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F605b0249e73a4fe185c0a075ee85c7a3_b4.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffb3679b641214f9b8af929cc58d1fe87_b5.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fd1cbc7c3e2e04aa28ed27b6913dbe05b_b6.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F3c081339d951490b8d232477d9249ec2_b7.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3b7302aa7944f7caad225fb32652999_b8.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F54a05d34d02141ee8c05a129a7cb3555_b9.jpeg"
                ),
                price = 499,
                sold = 0,
                content = "<p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F5c161af71062402d8dc7e3193e62d8f5_d1.png\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fea304cef45b846d2b7fc4e7fbef6d103_d2.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3d17dae77d144b9aa828537f96d04e4_d3.jpg\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F99710ccacd5443518a9b97386d028b5c_d4.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fa180b572f52142d5811dcf4e18c27a95_d5.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff5bab785f9d04ac38b35e10a1b63486e_d6.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F19f52075481c44a789dcf648e3f8a7aa_d7.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>",
                status = 1,
                sortNum = 0
            ),
            specs = listOf(
                GoodsSpec(
                    id = 1,
                    goodsId = 1,
                    name = "黑色 6GB+128GB",
                    price = 1299,
                    stock = 100,
                    sortNum = 1,

                    ),
                GoodsSpec(
                    id = 2,
                    goodsId = 1,
                    name = "白色 6GB+128GB",
                    price = 1299,
                    stock = 50,
                    sortNum = 2,

                    ),
                GoodsSpec(
                    id = 3,
                    goodsId = 1,
                    name = "黑色 8GB+256GB",
                    price = 1599,
                    stock = 30,
                    sortNum = 3,

                    ),
                GoodsSpec(
                    id = 4,
                    goodsId = 1,
                    name = "白色 8GB+256GB",
                    price = 1599,
                    stock = 25,
                    sortNum = 4,

                    ),
                GoodsSpec(
                    id = 5,
                    goodsId = 1,
                    name = "蓝色 8GB+256GB",
                    price = 1649,
                    stock = 15,
                    sortNum = 5,

                    ),
                GoodsSpec(
                    id = 6,
                    goodsId = 1,
                    name = "绿色 8GB+256GB",
                    price = 1649,
                    stock = 10,
                    sortNum = 6,

                    )
            ),
            selectedSpecIndex = 0,
            currentSpec = GoodsSpec(
                id = 1,
                goodsId = 1,
                name = "黑色 6GB+128GB",
                price = 1299,
                stock = 100,
                sortNum = 1,
            ),
            quantity = 1,
            isGridMode = false
        )
    }
}

/**
 * 规格选择底部弹出层预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun SpecSelectModalPreviewDark() {
    AppTheme(darkTheme = true) {
        SpecSelectModalContentView(
            goods = Goods(
                id = 1,
                createTime = "2025-03-29 00:17:15",
                updateTime = "2025-03-29 23:07:48",
                typeId = 11,
                title = "Redmi 14C",
                subTitle = "【持久续航】5160mAh 大电池",
                mainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
                pics = listOf(
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F32051f923ded432c82ef5934451a601b_b2.jpg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F88bf37e8c9ce42968067cbf3d717f613_b3.jpg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F605b0249e73a4fe185c0a075ee85c7a3_b4.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffb3679b641214f9b8af929cc58d1fe87_b5.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fd1cbc7c3e2e04aa28ed27b6913dbe05b_b6.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F3c081339d951490b8d232477d9249ec2_b7.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3b7302aa7944f7caad225fb32652999_b8.jpeg",
                    "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F54a05d34d02141ee8c05a129a7cb3555_b9.jpeg"
                ),
                price = 499,
                sold = 0,
                content = "<p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F5c161af71062402d8dc7e3193e62d8f5_d1.png\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fea304cef45b846d2b7fc4e7fbef6d103_d2.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3d17dae77d144b9aa828537f96d04e4_d3.jpg\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F99710ccacd5443518a9b97386d028b5c_d4.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fa180b572f52142d5811dcf4e18c27a95_d5.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff5bab785f9d04ac38b35e10a1b63486e_d6.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F19f52075481c44a789dcf648e3f8a7aa_d7.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>",
                status = 1,
                sortNum = 0
            ),
            specs = listOf(
                GoodsSpec(
                    id = 1,
                    goodsId = 1,
                    name = "黑色 6GB+128GB",
                    price = 1299,
                    stock = 100,
                    sortNum = 1,

                    ),
                GoodsSpec(
                    id = 2,
                    goodsId = 1,
                    name = "白色 6GB+128GB",
                    price = 1299,
                    stock = 50,
                    sortNum = 2,

                    ),
                GoodsSpec(
                    id = 3,
                    goodsId = 1,
                    name = "黑色 8GB+256GB",
                    price = 1599,
                    stock = 30,
                    sortNum = 3,

                    ),
                GoodsSpec(
                    id = 4,
                    goodsId = 1,
                    name = "白色 8GB+256GB",
                    price = 1599,
                    stock = 25,
                    sortNum = 4,

                    ),
                GoodsSpec(
                    id = 5,
                    goodsId = 1,
                    name = "蓝色 8GB+256GB",
                    price = 1649,
                    stock = 15,
                    sortNum = 5,

                    ),
                GoodsSpec(
                    id = 6,
                    goodsId = 1,
                    name = "绿色 8GB+256GB",
                    price = 1649,
                    stock = 10,
                    sortNum = 6,

                    )
            ),
            selectedSpecIndex = 0,
            currentSpec = GoodsSpec(
                id = 1,
                goodsId = 1,
                name = "黑色 6GB+128GB",
                price = 1299,
                stock = 100,
                sortNum = 1,
            ),
            quantity = 1,
            isGridMode = true
        )
    }
}