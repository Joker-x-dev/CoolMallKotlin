package com.joker.coolmall.feature.auth.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.component.CenterRow
import com.joker.coolmall.core.designsystem.theme.Primary

/**
 * 底部导航栏组件，用于显示登录、注册等页面的底部导航选项
 *
 * @param modifier 可选修饰符
 * @param messageText 提示文本
 * @param actionText 操作按钮文本
 * @param onCancelClick 左侧操作按钮的回调
 * @param onActionClick 右侧操作按钮的回调
 * @param divider 是否显示分隔符，默认为false
 * @author Joker.X
 */
@Composable
fun BottomNavigationRow(
    modifier: Modifier = Modifier,
    messageText: String,
    actionText: String,
    onCancelClick: () -> Unit = {},
    onActionClick: () -> Unit,
    divider: Boolean = false
) {
    CenterRow(
        modifier = modifier.padding(top = 32.dp),
    ) {
        if (!divider) {
            Text(
                text = messageText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        } else {
            TextButton(onClick = onCancelClick) {
                Text(
                    text = messageText,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        if (divider) {
            Text(
                text = "|",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }

        TextButton(onClick = onActionClick) {
            Text(
                text = actionText,
                color = if (divider) MaterialTheme.colorScheme.primary else Primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
} 