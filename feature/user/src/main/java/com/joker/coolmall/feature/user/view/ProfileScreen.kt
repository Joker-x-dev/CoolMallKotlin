package com.joker.coolmall.feature.user.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joker.coolmall.core.designsystem.component.CenterBox
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXSmall
import com.joker.coolmall.core.model.entity.User
import com.joker.coolmall.core.ui.component.image.SmallAvatar
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
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param viewModel 个人中心ViewModel
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ProfileRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    // 收集登录状态
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    // 收集用户信息
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()

    ProfileScreen(
        onBackClick = viewModel::navigateBack,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        isLoggedIn = isLoggedIn,
        userInfo = userInfo,
        routeAvatarUrl = viewModel.routeAvatarUrl
    )
}

/**
 * 个人中心界面
 *
 * @param onBackClick 返回上一页回调
 * @param isLoggedIn 登录状态
 * @param userInfo 用户信息
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun ProfileScreen(
    onBackClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    isLoggedIn: Boolean = false,
    userInfo: User? = null,
    routeAvatarUrl: String? = null
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
                        SmallAvatar(
                            avatarUrl = routeAvatarUrl ?: userInfo?.avatarUrl,
                            modifier = Modifier.let { modifier ->
                                if (sharedTransitionScope != null && animatedContentScope != null) {
                                    with(sharedTransitionScope) {
                                        modifier.sharedElement(
                                            state = rememberSharedContentState(key = "user_avatar"),
                                            animatedVisibilityScope = animatedContentScope
                                        )
                                    }
                                } else {
                                    modifier
                                }
                            }
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
                            userInfo?.nickName ?: "未设置",
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
                            userInfo?.phone ?: "未绑定",
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
                            userInfo?.id?.toString() ?: "未设置",
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
//        ProfileScreen()
    }
}

/**
 * 个人中心界面深色主题预览
 */
@Composable
@Preview(showBackground = true)
fun ProfileScreenPreviewDark() {
    AppTheme(darkTheme = true) {
//        ProfileScreen()
    }
}