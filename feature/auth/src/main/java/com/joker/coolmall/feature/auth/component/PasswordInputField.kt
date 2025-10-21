package com.joker.coolmall.feature.auth.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.feature.auth.R

/**
 * 密码输入组件，包含可切换密码显示/隐藏的按钮
 *
 * @param password 密码文本
 * @param onPasswordChange 密码变更回调
 * @param passwordFieldFocused 输入框焦点状态
 * @param placeholder 提示文本，默认为"请输入密码"
 * @param nextAction 输入法下一步动作，默认为Done
 * @param modifier 可选修饰符
 * @author Joker.X
 */
@Composable
fun PasswordInputField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordFieldFocused: MutableState<Boolean>,
    placeholder: String = "",
    nextAction: ImeAction = ImeAction.Done,
    modifier: Modifier = Modifier
) {
    val placeholderVisible = password.isEmpty()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier) {
        BasicTextField(
            value = password,
            onValueChange = onPasswordChange,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = nextAction
            ),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { passwordFieldFocused.value = it.isFocused }
        ) { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                if (placeholderVisible) {
                    Text(
                        text = placeholder.ifEmpty { stringResource(id = R.string.password_hint) },
                        color = Color.Gray,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                innerTextField()
            }
        }

        // 添加显示/隐藏密码的图标按钮
        IconButton(
            onClick = { passwordVisible = !passwordVisible },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
        ) {
            Image(
                painter = painterResource(
                    id = if (passwordVisible) R.drawable.ic_eye else R.drawable.ic_eye_hide
                ),
                contentDescription = if (passwordVisible)
                    stringResource(id = R.string.hide_password) else stringResource(id = R.string.show_password),
                modifier = Modifier.size(20.dp)
            )
        }
    }

    // 使用封装的底部线条组件
    FocusableDivider(focusState = passwordFieldFocused)
} 