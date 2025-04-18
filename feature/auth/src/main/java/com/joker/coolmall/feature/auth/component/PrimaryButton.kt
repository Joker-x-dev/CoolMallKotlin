package com.joker.coolmall.feature.auth.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.designsystem.theme.TitleLarge

/**
 * 主题色按钮组件，固定高度和圆角的按钮
 *
 * @param text 按钮文本
 * @param onClick 点击回调
 * @param enabled 是否启用，默认为true
 * @param modifier 可选修饰符
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    SpaceVerticalXLarge()
    
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Text(
            text = text,
            style = TitleLarge
        )
    }
} 