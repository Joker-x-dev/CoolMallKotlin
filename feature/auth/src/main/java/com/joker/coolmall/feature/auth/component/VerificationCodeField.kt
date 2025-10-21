package com.joker.coolmall.feature.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.theme.ColorWarning
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXLarge
import com.joker.coolmall.feature.auth.R

/**
 * 验证码输入组件，包含输入框与获取验证码按钮
 *
 * @param verificationCode 验证码文本
 * @param onVerificationCodeChange 验证码变更回调
 * @param codeFieldFocused 输入框焦点状态
 * @param onSendVerificationCode 发送验证码回调
 * @param placeholder 提示文本，默认为"验证码"
 * @param nextAction 输入法下一步动作，默认为Next
 * @param isPhoneValid 手机号是否有效，用于控制发送按钮是否可点击
 * @param modifier 可选修饰符
 * @author Joker.X
 */
@Composable
fun VerificationCodeField(
    verificationCode: String,
    onVerificationCodeChange: (String) -> Unit,
    codeFieldFocused: MutableState<Boolean>,
    onSendVerificationCode: () -> Unit,
    placeholder: String = "",
    nextAction: ImeAction = ImeAction.Next,
    isPhoneValid: Boolean = true,
    modifier: Modifier = Modifier
) {
    // 验证码输入框和发送按钮
    StartRow(modifier = modifier) {
        BasicTextField(
            value = verificationCode,
            onValueChange = { newValue ->
                // 只允许输入数字且限制长度为4位
                if (newValue.length <= 4 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
                    onVerificationCodeChange(newValue)
                }
            },
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
                        text = placeholder.ifEmpty { stringResource(id = R.string.verification_code) },
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        }

        Text(
            text = stringResource(id = R.string.get_verification_code),
            color = if (isPhoneValid) ColorWarning else Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = SpaceHorizontalXLarge)
                .clickable(enabled = isPhoneValid, onClick = onSendVerificationCode)
        )
    }

    // 使用封装的底部线条组件
    FocusableDivider(focusState = codeFieldFocused)
} 