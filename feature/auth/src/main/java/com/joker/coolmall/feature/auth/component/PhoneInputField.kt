package com.joker.coolmall.feature.auth.component

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
import com.joker.coolmall.core.designsystem.theme.Primary
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalXLarge
import com.joker.coolmall.feature.auth.R

/**
 * 手机号输入组件，包含国家代码与输入框
 *
 * @param phone 手机号文本
 * @param onPhoneChange 手机号变更回调
 * @param phoneFieldFocused 输入框焦点状态
 * @param placeholder 提示文本，默认为"请输入手机号"
 * @param nextAction 输入法下一步动作，默认为Next
 * @param modifier 可选修饰符
 * @author Joker.X
 */
@Composable
fun PhoneInputField(
    phone: String,
    onPhoneChange: (String) -> Unit,
    phoneFieldFocused: MutableState<Boolean>,
    placeholder: String = "",
    nextAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
) {
    // 手机号输入框及底部线条
    StartRow(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.phone_code),
            color = Primary,
            fontSize = 16.sp,
            modifier = Modifier.padding(end = SpaceHorizontalXLarge)
        )

        BasicTextField(
            value = phone,
            onValueChange = onPhoneChange,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = nextAction
            ),
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { phoneFieldFocused.value = it.isFocused }
        ) { innerTextField ->
            Box {
                if (phone.isEmpty()) {
                    Text(
                        text = placeholder.ifEmpty { stringResource(id = R.string.phone_hint) },
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        }
    }

    // 使用封装的底部线条组件
    FocusableDivider(focusState = phoneFieldFocused)
} 