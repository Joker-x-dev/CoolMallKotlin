package com.joker.coolmall.feature.cs.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.component.AppRow
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.ShapeExtraLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalSmall
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingSmall
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.button.ButtonSize
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.feature.cs.R

/**
 * 聊天输入栏组件
 *
 * @param inputText 输入文本
 * @param onInputTextChange 输入文本改变回调
 * @param showEmojiSelector 是否显示表情选择器
 * @param onEmojiSelectorToggle 表情选择器显示状态切换回调
 * @param onSendMessage 发送消息回调
 * @param onFunctionSelectorToggle 功能选择器显示状态切换回调
 * @param showFunctionSelector 是否显示功能选择器
 * @param focusRequester 输入框焦点请求器
 * @param onFocusRequested 输入框聚焦前的回调，用于处理前置逻辑
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun ChatInputBar(
    inputText: String,
    onInputTextChange: (String) -> Unit,
    showEmojiSelector: Boolean,
    onEmojiSelectorToggle: (Boolean) -> Unit,
    onSendMessage: () -> Unit,
    onFunctionSelectorToggle: (Boolean) -> Unit,
    showFunctionSelector: Boolean,
    focusRequester: FocusRequester,
    onFocusRequested: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    // 软键盘控制器，用于显示和隐藏键盘
    val keyboardController = LocalSoftwareKeyboardController.current

    // 检查输入框是否有内容
    val hasInputContent = inputText.isNotEmpty()

    Surface(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpacePaddingMedium, vertical = SpacePaddingSmall)
        ) {
            AppRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 自定义输入框
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = ShapeExtraLarge
                        )
                        .padding(horizontal = SpacePaddingMedium, vertical = 6.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = inputText,
                        onValueChange = onInputTextChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    onFocusRequested?.invoke()
                                }
                            },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        decorationBox = { innerTextField ->
                            Box {
                                if (inputText.isEmpty()) {
                                    AppText(
                                        text = stringResource(R.string.input_message_hint),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                            alpha = 0.6f
                                        ),
                                        size = TextSize.BODY_SMALL
                                    )
                                }
                                innerTextField()
                            }
                        },
                        singleLine = true
                    )
                }

                SpaceHorizontalSmall()

                // 表情按钮
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(ShapeCircle)
                        .clickable {
                            // 点击表情按钮时隐藏键盘
                            keyboardController?.hide()
                            onEmojiSelectorToggle(!showEmojiSelector)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_emoji_good),
                        contentDescription = stringResource(R.string.emoji),
                        modifier = Modifier.size(24.dp),
                        tint = if (showEmojiSelector)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                SpaceHorizontalSmall()

                // 根据输入框内容显示发送按钮或加号按钮
                AnimatedContent(
                    targetState = hasInputContent,
                    transitionSpec = {
                        (fadeIn() + scaleIn(initialScale = 0.8f)) togetherWith
                                (fadeOut() + scaleOut(targetScale = 0.8f))
                    },
                    label = "button_animation"
                ) { hasContent ->
                    if (hasContent) {
                        // 发送按钮
                        AppButton(
                            text = stringResource(R.string.send),
                            size = ButtonSize.MINI,
                            onClick = onSendMessage,
                            fullWidth = false,
                        )
                    } else {
                        // 加号按钮
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(ShapeCircle)
                                .clickable {
                                    // 点击加号按钮时隐藏键盘
                                    keyboardController?.hide()
                                    onFunctionSelectorToggle(!showFunctionSelector)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_circle),
                                contentDescription = stringResource(R.string.more_functions),
                                modifier = Modifier.size(24.dp),
                                tint = if (showFunctionSelector)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}