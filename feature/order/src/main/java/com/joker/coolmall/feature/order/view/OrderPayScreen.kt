package com.joker.coolmall.feature.order.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alipay.sdk.app.PayTask
import com.joker.coolmall.core.designsystem.component.CenterRow
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ColorDanger
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.ui.component.bottombar.AppBottomButton
import com.joker.coolmall.core.ui.component.button.CheckButton
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.viewmodel.OrderPayViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 订单支付路由
 *
 * @param viewModel 订单支付 ViewModel
 */
@Composable
internal fun OrderPayRoute(
    viewModel: OrderPayViewModel = hiltViewModel()
) {
    val price by viewModel.price.collectAsState()
    val alipayPayInfo by viewModel.alipayPayInfo.collectAsState()
    val context = LocalContext.current

    OrderPayScreen(
        price = price,
        onBackClick = viewModel::handleBackClick,
        onPayClick = viewModel::startAlipayPayment
    )

    // 处理支付宝支付
    LaunchedEffect(alipayPayInfo) {
        if (alipayPayInfo.isNotBlank()) {
            // 在子线程中执行支付宝支付
            val result = withContext(Dispatchers.IO) {
                PayTask(context as androidx.activity.ComponentActivity).payV2(
                    alipayPayInfo,
                    true
                )
            }
            // 处理支付结果
            viewModel.processAlipayResult(result)
        }
    }

    // 拦截系统返回按钮，使用自定义返回逻辑
    BackHandler {
        viewModel.handleBackClick()
    }
}

/**
 * 订单支付界面
 *
 * @param price 支付价格
 * @param onBackClick 返回按钮回调
 * @param onPayClick 支付按钮回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderPayScreen(
    price: Int = 0,
    onBackClick: () -> Unit = {},
    onPayClick: () -> Unit = {},
) {

    AppScaffold(
        title = R.string.order_pay,
        useLargeTopBar = true,
        onBackClick = onBackClick,
        bottomBar = {
            val priceText = if (price > 0) {
                "去支付 ¥$price"
            } else {
                "去支付"
            }
            AppBottomButton(
                text = priceText,
                onClick = onPayClick,
            )
        }
    ) {
        OrderPayContentView(
            price = price,
        )
    }
}

/**
 * 订单支付内容视图
 */
@Composable
private fun OrderPayContentView(
    price: Int
) {
    VerticalList(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpaceVerticalSmall()
        CenterRow {
            AppText(
                text = "¥",
                type = TextType.ERROR,
                size = TextSize.DISPLAY_LARGE,
                modifier = Modifier
                    .alignByBaseline()
                    .padding(end = 2.dp)
            )

            Text(
                text = price.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 42.sp,
                color = ColorDanger,
                modifier = Modifier.alignByBaseline()
            )

            AppText(
                text = ".00",
                type = TextType.ERROR,
                size = TextSize.DISPLAY_LARGE,
                modifier = Modifier.alignByBaseline()
            )
        }

        SpaceVerticalSmall()

        Card {
            AppListItem(
                leadingContent = {
                    Image(
                        painter = painterResource(id = com.joker.coolmall.core.ui.R.drawable.ic_alipay),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                title = "支付宝支付",
                showArrow = false,
                trailingContent = {
                    CheckButton(
                        selected = true,
                        onClick = {},
                        size = 22
                    )
                }
            )

            AppListItem(
                leadingContent = {
                    Image(
                        painter = painterResource(id = com.joker.coolmall.core.ui.R.drawable.ic_wechat),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                title = "微信支付",
                showArrow = false,
                trailingContent = {
                    CheckButton(
                        selected = false,
                        onClick = {},
                        size = 22
                    )
                }
            )

            AppListItem(
                leadingContent = {
                    Image(
                        painter = painterResource(id = com.joker.coolmall.core.ui.R.drawable.ic_qq),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                title = "QQ支付",
                showArrow = false,
                trailingContent = {
                    CheckButton(
                        selected = false,
                        onClick = {},
                        size = 22
                    )
                }
            )
        }
        AppText(
            "提示：订单价格经过处理，实际支付不会超过一元",
            type = TextType.TERTIARY,
            size = TextSize.BODY_SMALL
        )
    }
}

/**
 * 订单支付界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderPayScreenPreview() {
    AppTheme {
        OrderPayScreen(
            price = 9999,
        )
    }
}

/**
 * 订单支付界面深色主题预览
 */
@Preview(showBackground = true)
@Composable
internal fun OrderPayScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderPayScreen(
            price = 9999,
        )
    }
} 