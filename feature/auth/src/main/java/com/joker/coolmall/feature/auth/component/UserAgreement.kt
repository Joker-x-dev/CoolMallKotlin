package com.joker.coolmall.feature.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium

/**
 * 用户协议组件，显示用户协议与隐私政策链接
 *
 * @param prefix 协议前缀文本，例如"登录即代表您已阅读并同意"
 * @param onUserAgreementClick 用户协议点击回调
 * @param onPrivacyPolicyClick 隐私政策点击回调
 * @param modifier 可选修饰符
 */
@Composable
fun UserAgreement(
    prefix: String = "登录即代表您已阅读并同意",
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    StartRow(modifier = modifier) {
        Text(
            text = prefix,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = "《用户协议》",
            color = Primary,
            fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = onUserAgreementClick)
        )

        Text(
            text = "和",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = "《隐私政策》",
            color = Primary,
            fontSize = 12.sp,
            modifier = Modifier.clickable(onClick = onPrivacyPolicyClick)
        )
    }
    
    SpaceVerticalMedium()
} 