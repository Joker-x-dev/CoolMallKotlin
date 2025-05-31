package com.joker.coolmall.feature.user.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.CenterBox
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ShapeCircle
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.ui.component.image.NetWorkImage
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.scaffold.AppScaffold
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.feature.user.R
import com.joker.coolmall.feature.user.viewmodel.ProfileViewModel

/**
 * 个人中心路由
 *
 * @param viewModel 个人中心ViewModel
 */
@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        onBackClick = viewModel::navigateBack
    )
}

/**
 * 个人中心界面
 *
 * @param onBackClick 返回上一页回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(
    onBackClick: () -> Unit = {}
) {
    AppScaffold(
        title = R.string.profile_title,
        useLargeTopBar = true,
        onBackClick = onBackClick
    ) {
        VerticalList(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Card {
                AppListItem(
                    title = "头像",
                    showArrow = false,
                    verticalPadding = SpaceVerticalXSmall,
                    horizontalPadding = SpaceHorizontalLarge,
                    trailingContent = {
                        NetWorkImage(
                            model = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F6d416005e44546d581f5a9189a69c756_5%E5%A4%B4%E5%83%8F.png",
                            size = 38.dp,
                            cornerShape = ShapeCircle
                        )
                    }
                )

                AppListItem(
                    title = "昵称",
                    showArrow = false,
                    showDivider = false,
                    horizontalPadding = SpaceHorizontalLarge,
                    trailingContent = {
                        AppText(
                            "Joker.X",
                            type = TextType.TERTIARY
                        )
                    }
                )
            }

            TitleWithLine(
                text = "账号信息", modifier = Modifier
                    .padding(top = SpaceVerticalSmall)
            )

            Card {
                AppListItem(
                    title = "手机号",
                    showArrow = false,
                    horizontalPadding = SpaceHorizontalLarge,
                    trailingContent = {
                        AppText(
                            "18888888888",
                            type = TextType.TERTIARY
                        )
                    }
                )

                AppListItem(
                    title = "账号",
                    showArrow = false,
                    showDivider = false,
                    horizontalPadding = SpaceHorizontalLarge,
                    trailingContent = {
                        AppText(
                            " 100001",
                            type = TextType.TERTIARY
                        )
                    }
                )
            }

            TitleWithLine(
                text = "社交账号", modifier = Modifier
                    .padding(top = SpaceVerticalSmall)
            )

            Card {
                AppListItem(
                    title = "微信",
                    showArrow = false,
                    horizontalPadding = SpaceHorizontalLarge,
                    trailingContent = {
                        AppText(
                            "去绑定",
                            type = TextType.TERTIARY
                        )
                    }
                )

                AppListItem(
                    title = " QQ",
                    showArrow = false,
                    showDivider = false,
                    horizontalPadding = SpaceHorizontalLarge,
                    trailingContent = {
                        AppText(
                            " 已绑定",
                            type = TextType.TERTIARY
                        )
                    }
                )
            }

            TitleWithLine(
                text = "安全", modifier = Modifier
                    .padding(top = SpaceVerticalSmall)
            )

            Card {
                AppListItem(title = "修改密码", horizontalPadding = SpaceHorizontalLarge)

                AppListItem(
                    title = " 注销账户",
                    showDivider = false,
                    horizontalPadding = SpaceHorizontalLarge,
                )
            }

            Card {
                CenterBox(
                    padding = SpaceVerticalLarge,
                    fillMaxSize = false
                ) {
                    AppText(
                        "退出登录",
                        type = TextType.ERROR,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * 个人中心界面浅色主题预览
 */
@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen()
    }
}

/**
 * 个人中心界面深色主题预览
 */
@Composable
@Preview(showBackground = true)
fun ProfileScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ProfileScreen()
    }
}