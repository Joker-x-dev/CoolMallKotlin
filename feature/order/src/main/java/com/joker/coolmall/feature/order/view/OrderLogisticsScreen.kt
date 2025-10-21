package com.joker.coolmall.feature.order.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.common.base.state.BaseNetWorkUiState
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.model.entity.Logistics
import com.joker.coolmall.core.model.entity.Order
import com.joker.coolmall.core.model.preview.previewLogisticsData
import com.joker.coolmall.core.model.preview.previewOrder
import com.joker.coolmall.core.ui.component.address.AddressCard
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.network.BaseNetWorkView
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.order.R
import com.joker.coolmall.feature.order.component.LogisticsSteps
import com.joker.coolmall.feature.order.viewmodel.OrderLogisticsViewModel

/**
 * 订单物流路由
 *
 * @param viewModel 订单物流 ViewModel
 * @author Joker.X
 */
@Composable
internal fun OrderLogisticsRoute(
    viewModel: OrderLogisticsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState() // 订单数据状态
    val logisticsUiState by viewModel.orderLogisticsUiState.collectAsState() // 物流数据状态

    OrderLogisticsScreen(
        uiState = uiState,
        logisticsUiState = logisticsUiState,
        onBackClick = viewModel::navigateBack,
        onRetry = viewModel::retryRequest,
    )
}

/**
 * 订单物流界面
 *
 * @param uiState UI状态
 * @param logisticsUiState 物流数据状态
 * @param onBackClick 返回按钮回调
 * @param onRetry 重试请求回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrderLogisticsScreen(
    uiState: BaseNetWorkUiState<Order> = BaseNetWorkUiState.Loading,
    logisticsUiState: Logistics = Logistics(),
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    AppScaffold(
        titleText = stringResource(R.string.order_logistics),
        useLargeTopBar = true,
        onBackClick = onBackClick
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) { order ->
            OrderLogisticsContentView(
                order = order,
                logistics = logisticsUiState
            )
        }
    }
}

/**
 * 订单物流内容视图
 *
 * @param order 订单数据
 * @param logistics 物流数据
 * @author Joker.X
 */
@Composable
private fun OrderLogisticsContentView(
    order: Order,
    logistics: Logistics
) {
    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        // 地址卡片
        AddressCard(address = order.address)

        Card {
            AppListItem(
                title = "",
                showArrow = false,
                leadingContent = {
                    TitleWithLine(text = stringResource(R.string.logistics_info))
                },
                trailingContent = {
                    NetWorkImage(
                        model = logistics.logo,
                        size = 18.dp
                    )
                }
            )

            // 快递公司信息
            AppListItem(
                title = stringResource(R.string.express_company),
                showArrow = false,
                trailingText = logistics.expName ?: stringResource(R.string.unknown_express)
            )

            // 物流编号
            AppListItem(
                title = stringResource(R.string.logistics_number),
                trailingContent = {
                    AppText(
                        text = logistics.number ?: "",
                        type = TextType.SECONDARY,
                        size = TextSize.BODY_MEDIUM
                    )
                    SpaceHorizontalSmall()

                    AppText(
                        text = stringResource(R.string.copy),
                        type = TextType.LINK,
                        size = TextSize.BODY_MEDIUM,
                        onClick = { /* 复制物流编号 */ }
                    )
                    SpaceHorizontalXSmall()
                },
                showArrow = false
            )

            // 快递员信息
            AppListItem(
                title = stringResource(R.string.courier),
                showArrow = false,
                trailingText = logistics.courier
            )

            // 快递员电话
            AppListItem(
                title = stringResource(R.string.contact_phone),
                trailingContent = {
                    AppText(
                        text = logistics.courierPhone.toString(),
                        type = TextType.SECONDARY,
                        size = TextSize.BODY_MEDIUM
                    )
                    SpaceHorizontalSmall()

                    AppText(
                        text = stringResource(R.string.call),
                        type = TextType.LINK,
                        size = TextSize.BODY_MEDIUM,
                        onClick = { /* 拨打电话 */ }
                    )
                    SpaceHorizontalXSmall()
                },
                showArrow = false
            )

            // 耗时信息
            AppListItem(
                title = stringResource(R.string.transport_time),
                showArrow = false,
                trailingText = logistics.takeTime,
                showDivider = false
            )
        }

        // 物流轨迹卡片
        Card {
            AppListItem(
                title = "",
                showArrow = false,
                leadingContent = { TitleWithLine(text = stringResource(R.string.logistics_track)) }
            )

            // 物流步骤条
            LogisticsSteps(
                logisticsItems = logistics.list ?: emptyList(),
                modifier = Modifier.padding(SpacePaddingMedium)
            )
        }
    }
}

/**
 * 订单物流界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview
@Composable
private fun OrderLogisticsScreenPreview() {
    AppTheme {
        OrderLogisticsScreen(
            uiState = BaseNetWorkUiState.Success(previewOrder),
            logisticsUiState = previewLogisticsData
        )
    }
}

/**
 * 订单物流界面深色主题预览
 *
 * @author Joker.X
 */
@Preview
@Composable
private fun OrderLogisticsScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        OrderLogisticsScreen(
            uiState = BaseNetWorkUiState.Success(previewOrder),
            logisticsUiState = previewLogisticsData
        )
    }
}