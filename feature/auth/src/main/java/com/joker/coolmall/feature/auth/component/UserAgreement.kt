package com.joker.coolmall.feature.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.component.CenterRow
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.feature.auth.R

/**
 * 用户协议组件，显示用户协议与隐私政策链接
 *
 * @param modifier 可选修饰符
 * @param prefix 协议前缀文本，例如"登录即代表您已阅读并同意"
 * @param centerAlignment 是否居中对齐，默认为false（居左）
 * @param onUserAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 */
@Composable
fun UserAgreement(
    modifier: Modifier = Modifier,
    prefix: String = "",
    centerAlignment: Boolean = false,
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {}
) {
    val prefixText = if (prefix.isEmpty()) {
        stringResource(id = R.string.login_agreement_prefix)
    } else {
        prefix
    }

    // 根据centerAlignment参数选择不同的Row容器
    if (centerAlignment) {
        CenterRow(modifier = modifier) {
            AgreementContent(
                prefixText = prefixText,
                onUserAgreementClick = onUserAgreementClick,
                onPrivacyPolicyClick = onPrivacyPolicyClick
            )
        }
    } else {
        StartRow(modifier = modifier) {
            AgreementContent(
                prefixText = prefixText,
                onUserAgreementClick = onUserAgreementClick,
                onPrivacyPolicyClick = onPrivacyPolicyClick
            )
        }
        SpaceVerticalMedium()
    }
}

/**
 * 用户协议内容组件，抽取公共部分
 */
@Composable
private fun AgreementContent(
    prefixText: String,
    onUserAgreementClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    Text(
        text = prefixText,
        fontSize = 12.sp,
        color = Color.Gray
    )

    Text(
        text = stringResource(id = R.string.user_agreement),
        color = Primary,
        fontSize = 12.sp,
        modifier = Modifier.clickable(onClick = onUserAgreementClick)
    )

    Text(
        text = stringResource(id = R.string.and),
        fontSize = 12.sp,
        color = Color.Gray
    )

    Text(
        text = stringResource(id = R.string.privacy_policy),
        color = Primary,
        fontSize = 12.sp,
        modifier = Modifier.clickable(onClick = onPrivacyPolicyClick)
    )
} 