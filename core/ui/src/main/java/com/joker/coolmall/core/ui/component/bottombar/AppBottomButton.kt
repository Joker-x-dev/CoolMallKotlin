package com.joker.coolmall.core.ui.component.bottombar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.SpacePaddingMedium
import com.joker.coolmall.core.ui.component.button.AppButton

/**
 * 底部按钮栏组件
 *
 * @param text 按钮文本
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param enabled 是否启用，默认为true
 * @param loading 是否加载中，默认为false
 * @author Joker.X
 */
@Composable
fun AppBottomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        shadowElevation = 4.dp,
    ) {
        AppButton(
            text = text,
            onClick = onClick,
            enabled = enabled,
            loading = loading,
            modifier = modifier
                .navigationBarsPadding()
                .padding(SpacePaddingMedium)
        )
    }
}