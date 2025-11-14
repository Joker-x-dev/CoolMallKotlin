package com.joker.coolmall.feature.user.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joker.coolmall.core.designsystem.component.CenterBox
import com.joker.coolmall.core.designsystem.component.VerticalList
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
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
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun ProfileRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    // 收集用户信息
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()

    ProfileScreen(
        onBackClick = viewModel::navigateBack,
        onLogoutClick = viewModel::logout,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        userInfo = userInfo,
    )
}

/**
 * 个人中心界面
 *
 * @param onBackClick 返回上一页回调
 * @param onLogoutClick 退出登录回调
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param userInfo 用户信息
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    userInfo: User? = null,
) {
    AppScaffold(
        title = R.string.profile_title,
        useLargeTopBar = true,
        onBackClick = onBackClick
    ) {
        ProfileContentView(
            onLogoutClick = onLogoutClick,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope,
            userInfo = userInfo,
        )
    }
}

/**
 * 个人中心内容视图
 *
 * @param onLogoutClick 退出登录回调
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param userInfo 用户信息
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ProfileContentView(
    onLogoutClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    userInfo: User? = null,
) {
    VerticalList(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Card {
            AppListItem(
                title = stringResource(R.string.avatar),
                showArrow = false,
                verticalPadding = SpaceVerticalSmall,
                horizontalPadding = SpaceHorizontalLarge,
                trailingContent = {
                    SmallAvatar(
                        avatarUrl = userInfo?.avatarUrl,
                        modifier = Modifier.let { modifier ->
                            if (sharedTransitionScope != null && animatedContentScope != null) {
                                with(sharedTransitionScope) {
                                    modifier.sharedElement(
                                        sharedContentState = rememberSharedContentState(key = "user_avatar"),
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
                title = stringResource(R.string.nickname),
                showArrow = false,
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingContent = {
                    AppText(
                        userInfo?.nickName ?: stringResource(R.string.not_set),
                        type = TextType.TERTIARY
                    )
                }
            )
        }

        TitleWithLine(
            text = stringResource(R.string.account_info), modifier = Modifier
                .padding(top = SpaceVerticalSmall)
        )

        Card {
            AppListItem(
                title = stringResource(R.string.phone),
                showArrow = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingContent = {
                    AppText(
                        userInfo?.phone ?: stringResource(R.string.not_bound),
                        type = TextType.TERTIARY
                    )
                }
            )

            AppListItem(
                title = stringResource(R.string.account),
                showArrow = false,
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingContent = {
                    AppText(
                        userInfo?.id?.toString() ?: stringResource(R.string.not_set),
                        type = TextType.TERTIARY
                    )
                }
            )
        }

        TitleWithLine(
            text = stringResource(R.string.social_account), modifier = Modifier
                .padding(top = SpaceVerticalSmall)
        )

        Card {
            AppListItem(
                title = stringResource(R.string.wechat),
                showArrow = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingContent = {
                    AppText(
                        stringResource(R.string.go_bind),
                        type = TextType.TERTIARY
                    )
                }
            )

            AppListItem(
                title = stringResource(R.string.qq),
                showArrow = false,
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                trailingContent = {
                    AppText(
                        stringResource(R.string.bound),
                        type = TextType.TERTIARY
                    )
                }
            )
        }

        TitleWithLine(
            text = stringResource(R.string.security), modifier = Modifier
                .padding(top = SpaceVerticalSmall)
        )

        Card {
            AppListItem(
                title = stringResource(R.string.change_password),
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )

            AppListItem(
                title = stringResource(R.string.delete_account),
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }

        Card(
            modifier = Modifier.clickable { onLogoutClick() }
        ) {
            CenterBox(
                padding = SpaceVerticalLarge,
                fillMaxSize = false
            ) {
                AppText(
                    stringResource(R.string.logout),
                    type = TextType.ERROR,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * 个人中心界面浅色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen()
    }
}

/**
 * 个人中心界面深色主题预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview(showBackground = true)
fun ProfileScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        ProfileScreen()
    }
}