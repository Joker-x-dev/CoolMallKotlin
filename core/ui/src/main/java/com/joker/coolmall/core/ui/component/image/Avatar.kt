package com.joker.coolmall.core.ui.component.image


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.ui.R

/**
 * 用户头像组件
 *
 * 该组件封装了用户头像的显示逻辑，支持登录和未登录状态：
 * - 未登录状态：显示默认头像（白色背景 + 用户图标）
 * - 登录状态：显示用户的网络头像
 * - 支持点击事件
 *
 * @param avatarUrl 用户头像URL，为空或null时显示默认头像
 * @param size 头像大小，默认为36dp
 * @param cornerShape 头像形状，默认为圆形
 * @param contentScale 图片内容缩放模式，默认为Crop
 * @param onClick 点击头像的回调
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun Avatar(
    avatarUrl: String? = null,
    size: Dp = 36.dp,
    cornerShape: Shape = CircleShape,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val finalModifier = modifier
        .size(size)
        .clip(cornerShape)
        .let { mod ->
            if (onClick != null) {
                mod.clickable { onClick.invoke() }
            } else {
                mod
            }
        }

    // 判断是否显示默认头像
    val shouldShowDefaultAvatar = avatarUrl.isNullOrEmpty()

    if (shouldShowDefaultAvatar) {
        // 未登录状态：显示默认头像
        DefaultAvatar(
            size = size,
            modifier = finalModifier
        )
    } else {
        // 登录状态：显示网络头像
        NetWorkImage(
            model = avatarUrl,
            size = size,
            cornerShape = cornerShape,
            contentScale = contentScale,
            showBackground = true,
            modifier = finalModifier
        )
    }
}

/**
 * 默认头像组件（未登录状态）
 *
 * 显示白色背景和用户图标
 *
 * @param size 头像大小
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
private fun DefaultAvatar(
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.08f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_my_fill),
            contentDescription = stringResource(id = R.string.default_avatar),
            modifier = Modifier.size(size * 0.5f), // 图标大小为头像的一半
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    }
}

/**
 * 小尺寸头像组件
 *
 * 适用于聊天界面等需要小头像的场景
 *
 * @param avatarUrl 用户头像URL
 * @param size 头像大小，默认为36dp
 * @param onClick 点击头像的回调
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun SmallAvatar(
    avatarUrl: String? = null,
    size: Dp = 36.dp,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Avatar(
        avatarUrl = avatarUrl,
        size = size,
        onClick = onClick,
        modifier = modifier
    )
}

/**
 * 中等尺寸头像组件
 *
 * 适用于个人资料页面等场景
 *
 * @param avatarUrl 用户头像URL
 * @param size 头像大小，默认为48dp
 * @param onClick 点击头像的回调
 * @param modifier 修饰符
 * @author Joker.X
 */
@Composable
fun MediumAvatar(
    avatarUrl: String? = null,
    size: Dp = 48.dp,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Avatar(
        avatarUrl = avatarUrl,
        size = size,
        onClick = onClick,
        modifier = modifier
    )
}

/**
 * 默认头像预览
 */
@Preview(showBackground = true)
@Composable
private fun DefaultAvatarPreview() {
    AppTheme {
        DefaultAvatar(size = 36.dp)
    }
}
