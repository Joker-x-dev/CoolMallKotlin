package com.joker.coolmall.core.ui.component.coupon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.SpaceBetweenRow
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.CommonIcon
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.ShapeSmall
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.TextWhite
import com.joker.coolmall.core.model.entity.Coupon
import com.joker.coolmall.core.model.preview.previewAvailableCoupon
import com.joker.coolmall.core.model.preview.previewExpiredCoupon
import com.joker.coolmall.core.model.preview.previewMyCoupon
import com.joker.coolmall.core.model.preview.previewUnusedCoupon
import com.joker.coolmall.core.model.preview.previewUsedCoupon
import com.joker.coolmall.core.ui.R
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.divider.WeDivider
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.PriceText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 优惠券卡片模式
 *
 * @author Joker.X
 */
enum class CouponCardMode {
    RECEIVE,    // 领取模式
    SELECT,     // 选择模式
    VIEW        // 查看模式（我的优惠券）
}

/**
 * 优惠券状态（自动计算）
 *
 * @author Joker.X
 */
private enum class CouponStatus {
    AVAILABLE,    // 可用
    SELECTED,     // 已选择
    USED,         // 已使用
    EXPIRED,      // 已过期
    INSUFFICIENT  // 不满足使用条件
}

/**
 * 判断优惠券是否过期
 *
 * @param endTime 优惠券结束时间
 * @return 是否过期
 * @author Joker.X
 */
private fun isExpired(endTime: String?): Boolean {
    if (endTime.isNullOrEmpty()) return false
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val expireDate = dateFormat.parse(endTime)
        val currentDate = Date()
        expireDate?.before(currentDate) ?: false
    } catch (e: Exception) {
        false
    }
}

/**
 * 优惠券卡片组件
 *
 * 可复用的优惠券卡片，根据模式和优惠券数据自动判断状态
 *
 * @param modifier 修饰符
 * @param coupon 优惠券数据
 * @param mode 卡片模式（领取模式或选择模式）
 * @param isSelected 是否已选择（仅在选择模式下有效）
 * @param currentPrice 当前商品价格（用于判断是否满足使用条件），null表示不检查条件
 * @param onActionClick 操作按钮点击回调
 * @param showDescription 是否显示详细描述，默认为true
 * @param expandable 是否可展开查看详情，默认为true
 * @author Joker.X
 */
@Composable
fun CouponCard(
    modifier: Modifier = Modifier,
    coupon: Coupon,
    mode: CouponCardMode = CouponCardMode.RECEIVE,
    isSelected: Boolean = false,
    currentPrice: Double? = null,
    onActionClick: () -> Unit = {},
    showDescription: Boolean = true,
    expandable: Boolean = true
) {
    var isExpanded by remember { mutableStateOf(false) }

    // 计算优惠券状态
    val status = remember(coupon, mode, isSelected, currentPrice) {
        when {
            // 检查是否已使用
            coupon.useStatus == 1 -> CouponStatus.USED
            // 检查是否已过期
            coupon.useStatus == 2 || isExpired(coupon.endTime) -> CouponStatus.EXPIRED
            // 检查是否满足使用条件（仅在选择模式下且提供了当前价格时检查）
            mode == CouponCardMode.SELECT && currentPrice != null && coupon.condition != null -> {
                val condition = coupon.condition
                if (condition != null && currentPrice < condition.fullAmount) {
                    CouponStatus.INSUFFICIENT
                } else if (isSelected) {
                    CouponStatus.SELECTED
                } else {
                    CouponStatus.AVAILABLE
                }
            }
            // 选择模式下检查是否已选择
            mode == CouponCardMode.SELECT && isSelected -> CouponStatus.SELECTED
            // 其他情况为可用状态
            else -> CouponStatus.AVAILABLE
        }
    }

    // 判断是否为不可用状态（已使用、已过期或不满足条件）
    val isUnavailable =
        status == CouponStatus.USED || status == CouponStatus.EXPIRED || status == CouponStatus.INSUFFICIENT

    Card(modifier = modifier) {
        Box {
            // 主要内容
            CouponCardContent(
                coupon = coupon,
                status = status,
                mode = mode,
                isExpanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                onActionClick = onActionClick,
                showDescription = showDescription,
                expandable = expandable
            )

            // 不可用状态的灰色蒙版
            if (isUnavailable) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                        )
                )
            }
        }
    }
}

/**
 * 优惠券卡片内容
 *
 * @param coupon 优惠券数据
 * @param status 优惠券状态
 * @param mode 卡片模式
 * @param isExpanded 是否展开
 * @param onExpandedChange 展开状态变化回调
 * @param onActionClick 操作按钮点击回调
 * @param showDescription 是否显示描述
 * @param expandable 是否可展开
 * @author Joker.X
 */
@Composable
private fun CouponCardContent(
    coupon: Coupon,
    status: CouponStatus,
    mode: CouponCardMode,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onActionClick: () -> Unit,
    showDescription: Boolean,
    expandable: Boolean
) {
    Column {
        // 上半部分：优惠券主要信息
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpacePaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧优惠券图标
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Primary.copy(alpha = 0.9f),
                                Primary.copy(alpha = 0.7f)
                            )
                        ),
                        shape = ShapeSmall
                    ),
                contentAlignment = Alignment.Center
            ) {
                CommonIcon(
                    resId = R.drawable.ic_coupon,
                    size = 24.dp,
                    tint = TextWhite,
                )
            }

            SpaceHorizontalMedium()

            // 中间信息区域
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 优惠券标题
                AppText(
                    text = coupon.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                // 有效期
                coupon.endTime?.let { endTime ->
                    AppText(
                        text = stringResource(id = R.string.coupon_valid_until, endTime),
                        size = TextSize.BODY_MEDIUM,
                        type = TextType.TERTIARY
                    )
                }
            }

            SpaceHorizontalMedium()

            // 右侧金额和条件区域
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 优惠券金额
                PriceText(price = coupon.amount.toInt())
                // 使用条件
                coupon.condition?.let { condition ->
                    AppText(
                        text = stringResource(
                            id = R.string.coupon_conditions,
                            condition.fullAmount.toInt()
                        ),
                        size = TextSize.BODY_MEDIUM,
                        type = TextType.SECONDARY
                    )
                }
            }
        }

        // 分割线
        if (showDescription || expandable) {
            WeDivider()
        }

        // 下半部分：说明和操作按钮
        if (showDescription || expandable) {
            SpaceBetweenRow(
                modifier = Modifier
                    .padding(
                        horizontal = SpacePaddingMedium,
                        vertical = SpaceVerticalMedium
                    )
            ) {
                // 左侧：可展开的说明
                if (expandable && coupon.description.isNotEmpty()) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { onExpandedChange(!isExpanded) }
                        ) {
                            AppText(
                                text = stringResource(id = R.string.coupon_usage_instructions),
                                size = TextSize.BODY_MEDIUM,
                                type = TextType.TERTIARY
                            )

                            SpaceHorizontalXSmall()

                            CommonIcon(
                                resId = if (isExpanded) R.drawable.ic_up else R.drawable.ic_down,
                                size = 16.dp,
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            )
                        }

                        // 展开的详细说明
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            AppText(
                                text = coupon.description,
                                size = TextSize.BODY_MEDIUM,
                                type = TextType.TERTIARY,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                } else if (showDescription && coupon.description.isNotEmpty()) {
                    AppText(
                        text = coupon.description,
                        size = TextSize.BODY_MEDIUM,
                        type = TextType.TERTIARY,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // 右侧：操作按钮
                CouponActionButton(
                    status = status,
                    mode = mode,
                    onClick = onActionClick
                )
            }
        }
    }
}


/**
 * 优惠券操作按钮
 *
 * @param status 优惠券状态
 * @param mode 卡片模式
 * @param onClick 点击回调
 * @author Joker.X
 */
@Composable
private fun CouponActionButton(
    status: CouponStatus,
    mode: CouponCardMode,
    onClick: () -> Unit
) {
    when (status) {
        CouponStatus.AVAILABLE -> {
            val buttonTextResId = when (mode) {
                CouponCardMode.RECEIVE -> R.string.coupon_receive
                CouponCardMode.SELECT -> R.string.goods_select
                CouponCardMode.VIEW -> R.string.coupon_use
            }
            AppButton(
                text = stringResource(id = buttonTextResId),
                onClick = onClick,
                fullWidth = false,
                size = ButtonSize.MINI
            )
        }

        CouponStatus.SELECTED -> {
            AppText(
                text = stringResource(id = R.string.selected),
                type = TextType.LINK,
                size = TextSize.BODY_MEDIUM
            )
        }

        CouponStatus.USED -> {
            AppText(
                text = stringResource(id = R.string.coupon_used),
                type = TextType.TERTIARY,
                size = TextSize.BODY_MEDIUM
            )
        }

        CouponStatus.EXPIRED -> {
            AppText(
                text = stringResource(id = R.string.coupon_expired),
                type = TextType.TERTIARY,
                size = TextSize.BODY_MEDIUM
            )
        }

        CouponStatus.INSUFFICIENT -> {
            AppText(
                text = stringResource(id = R.string.coupon_insufficient),
                type = TextType.TERTIARY,
                size = TextSize.BODY_MEDIUM
            )
        }
    }
}

/**
 * 优惠券卡片预览 - 领取模式
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun CouponCardReceivePreview() {
    AppTheme {
        CouponCard(
            coupon = previewAvailableCoupon, // 可领取优惠券
            mode = CouponCardMode.RECEIVE
        )
    }
}

/**
 * 优惠券卡片预览 - 选择模式
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun CouponCardSelectPreview() {
    AppTheme {
        CouponCard(
            coupon = previewUnusedCoupon, // 未使用的优惠券
            mode = CouponCardMode.SELECT
        )
    }
}

/**
 * 优惠券卡片预览 - 查看模式
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun CouponCardViewPreview() {
    AppTheme {
        CouponCard(
            coupon = previewUnusedCoupon, // 预览优惠券
            mode = CouponCardMode.VIEW
        )
    }
}

/**
 * 优惠券卡片预览 - 已选择状态
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun CouponCardSelectedPreview() {
    AppTheme {
        CouponCard(
            coupon = previewUnusedCoupon, // 未使用的优惠券（选中状态）
            mode = CouponCardMode.SELECT,
            isSelected = true
        )
    }
}

/**
 * 优惠券卡片预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun CouponCardDarkPreview() {
    AppTheme(darkTheme = true) {
        CouponCard(
            coupon = previewMyCoupon, // 我的优惠券（深色主题）
            mode = CouponCardMode.RECEIVE
        )
    }
}

/**
 * 优惠券卡片预览 - 已使用状态
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun CouponCardUsedPreview() {
    AppTheme {
        CouponCard(
            coupon = previewUsedCoupon, // 已使用的优惠券
            mode = CouponCardMode.SELECT
        )
    }
}

/**
 * 优惠券卡片预览 - 已过期状态
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
private fun CouponCardExpiredPreview() {
    AppTheme {
        CouponCard(
            coupon = previewExpiredCoupon, // 已过期的优惠券
            mode = CouponCardMode.SELECT
        )
    }
}