package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.FullScreenBox
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.model.entity.Cart
import com.joker.coolmall.core.model.entity.ConfirmOrder
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.preview.previewAddress
import com.joker.coolmall.core.model.preview.previewCartList
import com.joker.coolmall.core.ui.component.address.AddressCard
import com.joker.coolmall.core.ui.component.button.AppButtonFixed
import com.joker.coolmall.core.ui.component.button.ButtonShape
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.button.ButtonStyle
import com.joker.coolmall.core.ui.component.card.AppCard
import com.joker.coolmall.core.ui.component.coupon.CouponCardMode
import com.joker.coolmall.core.ui.component.empty.EmptyNetwork
import com.joker.coolmall.core.ui.component.goods.OrderGoodsCard
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.loading.PageLoading
import com.joker.coolmall.core.ui.component.modal.CouponModal
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.extension.observeResult
import com.joker.coolmall.feature.order.viewmodel.OrderConfirmViewModel
import com.joker.coolmall.navigation.results.SelectAddressResultKey
import com.joker.coolmall.core.ui.R as CoreUiR

/**
 * 确认订单路由
 *
 * @param viewModel 确认订单ViewModel
 * @param navController 导航控制器
 * @author Joker.X
 */
@Composable
internal fun OrderConfirmRoute(
    viewModel: OrderConfirmViewModel = hiltViewModel(),
    navController: NavController
) {
    // UI状态
    val uiState by viewModel.uiState.collectAsState()
    // 订单备注
    val remark by viewModel.remark.collectAsState()
    // 优惠券弹窗显示状态
    val couponModalVisible by viewModel.couponModalVisible.collectAsState()
    // 选中的优惠券
    val selectedCoupon by viewModel.selectedCoupon.collectAsState()
    // 原始价格
    val originalPrice by viewModel.originalPrice.collectAsState()
    // 折扣金额
    val discountAmount by viewModel.discountAmount.collectAsState()
    // 总价格
    val totalPrice by viewModel.totalPrice.collectAsState()

    OrderConfirmScreen(
        uiState = uiState,
        onRetry = viewModel::retryRequest,
        onBackClick = viewModel::navigateBack,
        cartList = viewModel.cartList,
        remark = remark,
        onRemarkChange = viewModel::updateRemark,
        onSubmitOrderClick = viewModel::onSubmitOrderClick,
        couponModalVisible = couponModalVisible,
        selectedCoupon = selectedCoupon,
        originalPrice = originalPrice,
        discountAmount = discountAmount,
        totalPrice = totalPrice,
        onShowCouponModal = viewModel::showCouponModal,
        onHideCouponModal = viewModel::hideCouponModal,
        onSelectCoupon = viewModel::selectCoupon,
        onAddressClick = viewModel::navigateToAddressSelection
    )

    // 使用类型安全的 NavigationResultKey 监听地址选择结果
    navController.observeResult(SelectAddressResultKey) { address ->
        viewModel.onAddressSelected(address)
    }
}

/**
 * 确认订单页面
 *
 * @param uiState UI状态
 * @param onRetry 重试请求回调
 * @param onBackClick 返回按钮回调
 * @param cartList 购物车列表
 * @param remark 订单备注
 * @param onRemarkChange 订单备注变更回调
 * @param onSubmitOrderClick 提交订单点击回调
 * @param couponModalVisible 优惠券弹出层显示状态
 * @param selectedCoupon 选中的优惠券
 * @param originalPrice 原始价格
 * @param discountAmount 折扣金额
 * @param totalPrice 总价格
 * @param onShowCouponModal 显示优惠券弹出层回调
 * @param onHideCouponModal 隐藏优惠券弹出层回调
 * @param onSelectCoupon 选择优惠券回调
 * @param onAddressClick 地址点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderConfirmScreen(
    uiState: BaseNetWorkUiState<ConfirmOrder> = BaseNetWorkUiState.Loading,
    onRetry: () -> Unit = {},
    onBackClick: () -> Unit = {},
    cartList: List<Cart> = emptyList(),
    remark: String = "",
    onRemarkChange: (String) -> Unit = {},
    onSubmitOrderClick: () -> Unit = {},
    couponModalVisible: Boolean = false,
    selectedCoupon: Coupon? = null,
    originalPrice: Double = 0.0,
    discountAmount: Double = 0.0,
    totalPrice: Double = 0.0,
    onShowCouponModal: () -> Unit = {},
    onHideCouponModal: () -> Unit = {},
    onSelectCoupon: (Coupon?) -> Unit = {},
    onAddressClick: () -> Unit = {}
) {
    AppScaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding(),
        title = R.string.order_confirm,
        useLargeTopBar = true,
        onBackClick = onBackClick,
        contentShouldConsumePadding = true,
        bottomBar = {
            if (uiState is BaseNetWorkUiState.Success) {
                OrderBottomBar(
                    totalPrice = totalPrice,
                    onSubmitClick = onSubmitOrderClick
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            BaseNetWorkView(
                uiState = uiState,
                customLoading = { FullScreenBox { PageLoading() } },
                customError = { EmptyNetwork(onRetryClick = onRetry) }
            ) {
                OrderConfirmContentView(
                    pageData = it,
                    originalPrice = originalPrice,
                    discountAmount = discountAmount,
                    totalPrice = totalPrice,
                    cartList = cartList,
                    remark = remark,
                    onRemarkChange = onRemarkChange,
                    selectedCoupon = selectedCoupon,
                    onShowCouponModal = onShowCouponModal,
                    onAddressClick = onAddressClick
                )
            }
        }

        // 优惠券弹出层
        CouponModal(
            visible = couponModalVisible,
            onDismiss = onHideCouponModal,
            coupons = when (uiState) {
                is BaseNetWorkUiState.Success -> uiState.data.userCoupon ?: emptyList()
                else -> emptyList()
            },
            title = stringResource(R.string.select_coupon),
            mode = CouponCardMode.SELECT,
            currentPrice = originalPrice,
            onCouponAction = { couponId ->
                // 根据ID找到对应的优惠券
                val coupon = when (uiState) {
                    is BaseNetWorkUiState.Success -> {
                        (uiState.data.userCoupon ?: emptyList()).find { it.id == couponId }
                    }

                    else -> null
                }
                onSelectCoupon(coupon)
            }
        )
    }
}

/**
 * 确认订单内容视图
 *
 * @param pageData 页面数据
 * @param originalPrice 原始价格
 * @param discountAmount 折扣金额
 * @param totalPrice 总价格
 * @param cartList 购物车列表
 * @param remark 订单备注
 * @param onRemarkChange 订单备注变更回调
 * @param selectedCoupon 选中的优惠券
 * @param onShowCouponModal 显示优惠券弹出层回调
 * @param onAddressClick 地址点击回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderConfirmContentView(
    pageData: ConfirmOrder,
    originalPrice: Double,
    discountAmount: Double,
    totalPrice: Double,
    cartList: List<Cart>,
    remark: String,
    onRemarkChange: (String) -> Unit,
    selectedCoupon: Coupon? = null,
    onShowCouponModal: () -> Unit = {},
    onAddressClick: () -> Unit = {}
) {
    VerticalList {
        // 地址选择卡片
        AddressCard(
            address = pageData.defaultAddress,
            onClick = onAddressClick,
            addressSelected = true
        )

        // 订单商品卡片
        cartList.forEach { cart ->
            OrderGoodsCard(
                data = cart,
                enableQuantityStepper = false
            )
        }

        // 价格明细卡片
        Card {
            AppListItem(
                title = "",
                showArrow = false,
                leadingContent = {
                    TitleWithLine(text = stringResource(R.string.price_detail))
                }
            )

            AppListItem(
                title = stringResource(R.string.goods_total_price),
                leadingIcon = R.drawable.ic_shop,
                trailingContent = {
                    PriceText(
                        originalPrice.toInt(), integerTextSize = TextSize.BODY_LARGE,
                        decimalTextSize = TextSize.BODY_SMALL,
                        symbolTextSize = TextSize.BODY_SMALL,
                        type = TextType.PRIMARY
                    )
                },
                showArrow = false
            )

            AppListItem(
                title = stringResource(R.string.coupon),
                leadingIcon = CoreUiR.drawable.ic_coupon,
                trailingText = selectedCoupon?.title ?: stringResource(R.string.coupon_select),
                showArrow = true,
                onClick = onShowCouponModal
            )

            // 显示优惠券折扣（仅当有折扣时显示）
            if (discountAmount > 0) {
                AppListItem(
                    title = stringResource(R.string.coupon_discount),
                    leadingIcon = CoreUiR.drawable.ic_refund,
                    trailingContent = {
                        PriceText(
                            -discountAmount.toInt(), integerTextSize = TextSize.BODY_LARGE,
                            decimalTextSize = TextSize.BODY_SMALL,
                            symbolTextSize = TextSize.BODY_SMALL,
                            type = TextType.ERROR
                        )
                    },
                    showArrow = false
                )
            }

            AppListItem(
                title = stringResource(R.string.total),
                leadingIcon = R.drawable.ic_money,
                trailingContent = {
                    PriceText(
                        totalPrice.toInt(), integerTextSize = TextSize.BODY_LARGE,
                        decimalTextSize = TextSize.BODY_SMALL,
                        symbolTextSize = TextSize.BODY_SMALL,
                        type = TextType.PRIMARY
                    )
                },
                showArrow = false,
                showDivider = false
            )
        }

        // 订单备注卡片
        AppCard(lineTitle = stringResource(R.string.order_remark_title)) {
            OutlinedTextField(
                value = remark,
                onValueChange = onRemarkChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                label = { Text(stringResource(R.string.order_remark_label)) },
                placeholder = { Text(stringResource(R.string.order_remark_placeholder)) },
                shape = ShapeMedium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                minLines = 3,
                maxLines = 5
            )
        }
    }
}

/**
 * 订单底部操作栏
 *
 * @param totalPrice 订单总金额（单位：分）
 * @param onSubmitClick 提交订单点击回调
 * @author Joker.X
 */
@Composable
private fun OrderBottomBar(
    totalPrice: Double,
    onSubmitClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shadowElevation = 4.dp,
    ) {

        SpaceBetweenRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium)
        ) {

            PriceText(
                totalPrice.toInt(),
                integerTextSize = TextSize.DISPLAY_LARGE,
                decimalTextSize = TextSize.TITLE_MEDIUM,
                symbolTextSize = TextSize.TITLE_MEDIUM,
            )

            AppButtonFixed(
                text = stringResource(R.string.submit_order),
                onClick = onSubmitClick,
                size = ButtonSize.MINI,
                style = ButtonStyle.GRADIENT,
                shape = ButtonShape.SQUARE
            )
        }
    }
}

/**
 * 确认订单界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun OrderConfirmScreenPreview() {
    AppTheme {
        OrderConfirmScreen(
            uiState = BaseNetWorkUiState.Success(
                data = ConfirmOrder(
                    defaultAddress = previewAddress,
                    userCoupon = emptyList()
                )
            ),
            cartList = previewCartList,
        )
    }
}

/**
 * 确认订单界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun OrderConfirmScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderConfirmScreen(
            uiState = BaseNetWorkUiState.Success(
                data = ConfirmOrder(
                    defaultAddress = previewAddress,
                    userCoupon = emptyList()
                )
            ),
            cartList = previewCartList,
        )
    }
}