package com.joker.coolmall.feature.cs.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

/**
 * 聊天输入区域组件，包含输入栏、表情选择器和功能选择器
 *
 * @param inputText 输入文本
 * @param onInputTextChange 输入文本改变回调
 * @param onSendMessage 发送消息回调
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun ChatInputArea(
    inputText: String,
    onInputTextChange: (String) -> Unit,
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 是否显示表情选择器
    val (showEmojiSelector, setShowEmojiSelector) = remember {
        androidx.compose.runtime.mutableStateOf(
            false
        )
    }
    // 是否显示功能选择器
    val (showFunctionSelector, setShowFunctionSelector) = remember {
        androidx.compose.runtime.mutableStateOf(
            false
        )
    }

    // 键盘控制器
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val inputFieldFocusRequester = remember { FocusRequester() }

    // 隐藏键盘并清除焦点
    val hideKeyboard = {
        keyboardController?.hide()
        focusManager.clearFocus()
    }

    // 确保表情选择器和功能选择器互斥，并处理键盘
    val handleEmojiSelectorToggle: (Boolean) -> Unit = { show ->
        if (show) {
            // 如果要显示表情，先隐藏键盘
            hideKeyboard()
            setShowFunctionSelector(false)
        }
        setShowEmojiSelector(show)
    }

    val handleFunctionSelectorToggle: (Boolean) -> Unit = { show ->
        if (show) {
            // 如果要显示功能，先隐藏键盘
            hideKeyboard()
            setShowEmojiSelector(false)
        }
        setShowFunctionSelector(show)
    }

    // 面板高度动画
    val emojiSelectorHeight by animateDpAsState(
        targetValue = if (showEmojiSelector) 240.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "emojiSelectorHeight"
    )

    val functionSelectorHeight by animateDpAsState(
        targetValue = if (showFunctionSelector) 130.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "functionSelectorHeight"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        // 底部输入框
        ChatInputBar(
            inputText = inputText,
            onInputTextChange = onInputTextChange,
            showEmojiSelector = showEmojiSelector,
            onEmojiSelectorToggle = handleEmojiSelectorToggle,
            onSendMessage = onSendMessage,
            onFunctionSelectorToggle = handleFunctionSelectorToggle,
            showFunctionSelector = showFunctionSelector,
            focusRequester = inputFieldFocusRequester,
            onFocusRequested = {
                // 在输入框聚焦前关闭表情选择器和功能选择器
                setShowEmojiSelector(false)
                setShowFunctionSelector(false)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // 表情选择器 - 使用高度动画
        if (emojiSelectorHeight > 0.dp) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(emojiSelectorHeight)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                EmojiSelector(
                    onEmojiSelected = { emoji ->
                        onInputTextChange(inputText + emoji)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // 功能选择器 - 使用高度动画
        if (functionSelectorHeight > 0.dp) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(functionSelectorHeight)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                FunctionSelector(
                    onFunctionSelected = { function ->
                        // TODO: 处理功能选择
                        setShowFunctionSelector(false)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}