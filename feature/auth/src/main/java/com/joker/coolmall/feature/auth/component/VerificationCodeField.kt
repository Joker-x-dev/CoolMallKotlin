package com.joker.coolmall.feature.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.theme.ColorWarning
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge

/**
 * 验证码输入组件，包含输入框与获取验证码按钮
 *
 * @param verificationCode 验证码文本
 * @param onVerificationCodeChange 验证码变更回调
 * @param codeFieldFocused 输入框焦点状态
 * @param onSendVerificationCode 发送验证码回调
 * @param placeholder 提示文本，默认为"验证码"
 * @param nextAction 输入法下一步动作，默认为Next
 * @param modifier 可选修饰符
 */
@Composable
fun VerificationCodeField(
    verificationCode: String,
    onVerificationCodeChange: (String) -> Unit,
    codeFieldFocused: MutableState<Boolean>,
    onSendVerificationCode: () -> Unit,
    placeholder: String = "验证码",
    nextAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
) {
    // 验证码输入框和发送按钮
    StartRow(modifier = modifier) {
        BasicTextField(
            value = verificationCode,
            onValueChange = onVerificationCodeChange,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = nextAction
            ),
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { codeFieldFocused.value = it.isFocused }
        ) { innerTextField ->
            Box {
                if (verificationCode.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        }

        Text(
            text = "获取验证码",
            color = ColorWarning,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = SpaceHorizontalXLarge)
                .clickable(onClick = onSendVerificationCode)
        )
    }

    SpaceVerticalLarge()

    // 底部线条
    HorizontalDivider(
        thickness = 1.dp,
        color = if (codeFieldFocused.value) Primary else MaterialTheme.colorScheme.outline
    )
} 