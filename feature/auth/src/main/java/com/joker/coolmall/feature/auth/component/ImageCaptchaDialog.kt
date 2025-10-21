package com.joker.coolmall.feature.auth.component

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joker.coolmall.core.designsystem.component.StartRow
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.model.entity.Captcha
import com.joker.coolmall.core.ui.component.button.AppButton
import com.joker.coolmall.core.ui.component.modal.BottomModal
import com.joker.coolmall.feature.auth.R

/**
 * 图片验证码对话框组件
 *
 * @param visible 是否显示对话框
 * @param captcha 图片验证码数据
 * @param onDismiss 取消回调
 * @param onConfirm 确认回调，参数为用户输入的验证码
 * @param onRefreshCaptcha 刷新验证码回调，用户点击图片时触发
 * @param title 对话框标题，默认使用 R.string.security_verification
 * @param buttonText 按钮文本，默认使用 R.string.complete_verification
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCaptchaDialog(
    visible: Boolean,
    captcha: Captcha,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    onRefreshCaptcha: () -> Unit = {},
    title: String = stringResource(id = R.string.security_verification),
    buttonText: String = stringResource(id = R.string.complete_verification)
) {
    BottomModal(
        visible = visible,
        title = title,
        onDismiss = onDismiss
    ) {
        val imageCodeFieldFocused = remember { mutableStateOf(false) }
        val imageCode = remember { mutableStateOf("") }

        StartRow {
            BasicTextField(
                value = imageCode.value,
                onValueChange = { newValue ->
                    // 只允许输入数字且限制长度为4位
                    if (newValue.length <= 4 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
                        imageCode.value = newValue
                    }
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .onFocusChanged { imageCodeFieldFocused.value = it.isFocused }
            ) { innerTextField ->
                Box {
                    if (imageCode.value.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.image_captcha_hint),
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
            CaptchaImage(
                captcha = captcha,
                onRefresh = onRefreshCaptcha
            )
        }

        FocusableDivider(focusState = imageCodeFieldFocused)

        SpaceVerticalXLarge()

        // 根据验证码长度启用/禁用按钮
        AppButton(
            text = buttonText,
            onClick = { onConfirm(imageCode.value) },
            enabled = imageCode.value.length == 4
        )
    }
}

/**
 * 验证码图片组件
 *
 * @param modifier 修饰符
 * @param captcha 验证码数据
 * @param onRefresh 刷新验证码回调，点击图片时触发
 * @author Joker.X
 */
@Composable
fun CaptchaImage(
    modifier: Modifier = Modifier,
    captcha: Captcha,
    onRefresh: () -> Unit = {}
) {
    // 确保验证码数据不为空
    if (captcha.data.isBlank()) {
        // 显示点击刷新的占位区域
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .height(40.dp)
                .width(120.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .clickable(onClick = onRefresh)
        ) {
            Text(
                text = stringResource(id = R.string.refresh_captcha),
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    // 处理Base64数据
    val imageData = captcha.data
    val pureBase64 = if (imageData.contains("base64,")) {
        imageData.substringAfter("base64,")
    } else {
        imageData
    }

    // 尝试解码Base64数据
    val decodedBytes = try {
        Base64.decode(pureBase64, Base64.DEFAULT)
    } catch (e: Exception) {
        Log.e("CaptchaImage", "Base64解码失败: ${e.message}")
        null
    }

    // 检查解码是否成功
    if (decodedBytes == null || decodedBytes.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .height(40.dp)
                .width(120.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .clickable(onClick = onRefresh)
        ) {
            Text(
                text = stringResource(id = R.string.refresh_captcha),
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    // 尝试创建位图
    val bitmap = try {
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        Log.e("CaptchaImage", "位图创建失败: ${e.message}")
        null
    }

    // 检查位图是否创建成功
    if (bitmap == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .height(40.dp)
                .width(120.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .clickable(onClick = onRefresh)
        ) {
            Text(
                text = stringResource(id = R.string.refresh_captcha),
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    // 显示验证码图片
    Image(
        painter = BitmapPainter(bitmap.asImageBitmap()),
        contentDescription = stringResource(id = R.string.image_captcha_description),
        modifier = modifier
            .height(40.dp)
            .width(120.dp)
            .clickable(onClick = onRefresh)
    )
} 