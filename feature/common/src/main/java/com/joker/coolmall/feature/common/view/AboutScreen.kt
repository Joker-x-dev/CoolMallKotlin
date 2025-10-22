package com.joker.coolmall.feature.common.view

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.joker.coolmall.core.designsystem.component.CenterColumn
import com.joker.coolmall.core.designsystem.component.FullScreenBox
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.core.designsystem.theme.ArrowLeftIcon
import com.joker.coolmall.core.designsystem.theme.LogoIcon
import com.joker.coolmall.core.designsystem.theme.ShapeExtraLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceHorizontalMedium
import com.joker.coolmall.core.designsystem.theme.SpacePaddingLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalLarge
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalMedium
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalSmall
import com.joker.coolmall.core.designsystem.theme.SpaceVerticalXLarge
import com.joker.coolmall.core.ui.component.list.AppListItem
import com.joker.coolmall.core.ui.component.text.AppText
import com.joker.coolmall.core.ui.component.text.TextSize
import com.joker.coolmall.core.ui.component.text.TextType
import com.joker.coolmall.core.ui.component.title.TitleWithLine
import com.joker.coolmall.core.util.`package`.PackageUtils
import com.joker.coolmall.feature.common.R
import com.joker.coolmall.feature.common.component.DependencyModal
import com.joker.coolmall.feature.common.viewmodel.AboutViewModel
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * 关于我们路由
 *
 * @param viewModel 关于我们 ViewModel
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutRoute(
    viewModel: AboutViewModel = hiltViewModel()
) {

    // 收集依赖弹出层显示状态
    val showDependencyModal by viewModel.showDependencyModal.collectAsState()

    AboutScreen(
        onBackClick = viewModel::navigateBack,
        onDeveloperClick = viewModel::onDeveloperClick,
        onContributorsClick = viewModel::onContributorsClick,
        onGitHubClick = viewModel::onGitHubClick,
        onGiteeClick = viewModel::onGiteeClick,
        onApiDocClick = viewModel::onApiDocClick,
        onDemoDownloadClick = viewModel::onDemoDownloadClick,
        onIconSourceClick = viewModel::onIconSourceClick,
        onGitHubDiscussionClick = viewModel::onGitHubDiscussionClick,
        onQQGroupClick = viewModel::onQQGroupClick,
        onWeChatGroupClick = viewModel::onWeChatGroupClick,
        onTranslationClick = viewModel::onTranslationClick,
        onSupportClick = viewModel::onSupportClick,
        onHelpClick = viewModel::onHelpClick,
        onCitationClick = viewModel::onCitationClick,
        onUserAgreementClick = viewModel::onUserAgreementClick,
        onPrivacyPolicyClick = viewModel::onPrivacyPolicyClick,
        onOpenSourceLicenseClick = viewModel::onOpenSourceLicenseClick
    )

    DependencyModal(
        visible = showDependencyModal,
        dependencies = viewModel.dependencies,
        onDismiss = viewModel::onDismissDependencyModal,
        onDependencyClick = viewModel::onDependencyClick
    )
}

/**
 * 关于我们界面
 *
 * @param onBackClick 返回按钮回调
 * @param onDeveloperClick 点击开发者信息回调
 * @param onContributorsClick 点击贡献者列表回调
 * @param onGitHubClick 点击GitHub项目地址回调
 * @param onGiteeClick 点击Gitee项目地址回调
 * @param onApiDocClick 点击API文档回调
 * @param onDemoDownloadClick 点击Demo下载回调
 * @param onIconSourceClick 点击图标来源回调
 * @param onGitHubDiscussionClick 点击GitHub讨论区回调
 * @param onQQGroupClick 点击QQ交流群回调
 * @param onWeChatGroupClick 点击微信交流群回调
 * @param onTranslationClick 点击翻译帮助回调
 * @param onSupportClick 点击支持项目回调
 * @param onHelpClick 点击帮助与反馈回调
 * @param onCitationClick 点击引用致谢回调
 * @param onUserAgreementClick 点击用户协议回调
 * @param onPrivacyPolicyClick 点击隐私政策回调
 * @param onOpenSourceLicenseClick 点击开源许可回调
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutScreen(
    onBackClick: () -> Unit = {},
    onDeveloperClick: () -> Unit = {},
    onContributorsClick: () -> Unit = {},
    onGitHubClick: () -> Unit = {},
    onGiteeClick: () -> Unit = {},
    onApiDocClick: () -> Unit = {},
    onDemoDownloadClick: () -> Unit = {},
    onIconSourceClick: () -> Unit = {},
    onGitHubDiscussionClick: () -> Unit = {},
    onQQGroupClick: () -> Unit = {},
    onWeChatGroupClick: () -> Unit = {},
    onTranslationClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onCitationClick: () -> Unit = {},
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onOpenSourceLicenseClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState(0)
    val uriHandler = LocalUriHandler.current

    val scrollFadeThresholdPx = with(LocalDensity.current) { 220.dp.toPx() }
    val scrollFraction = (scrollState.value / scrollFadeThresholdPx).coerceIn(0f, 1f)

    // 入场动画状态
    var isAnimationReady by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        // 添加一个短暂的延迟，确保动画平滑启动
        delay(100)
        isAnimationReady = true
    }

    val entranceTranslationY by animateDpAsState(
        targetValue = if (isAnimationReady) 0.dp else 30.dp,
        animationSpec = tween(durationMillis = 1000, easing = EaseOutCubic),
        label = "entrance_translation"
    )

    val entranceAlpha by animateFloatAsState(
        targetValue = if (isAnimationReady) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "entrance_alpha"
    )

    val contentAlpha by animateFloatAsState(
        targetValue = 1f - scrollFraction,
        label = "content_alpha"
    )

    val contentScale by animateFloatAsState(
        targetValue = 1f - scrollFraction * 0.1f, // 缩小10%
        label = "content_scale"
    )

    val toolbarAlpha by animateFloatAsState(
        targetValue = scrollFraction,
        label = "toolbar_alpha"
    )

    FullScreenBox(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 动态极光背景 (淡出 + 入场动画)
        Box(modifier = Modifier.graphicsLayer(alpha = contentAlpha * entranceAlpha)) {
            AnimatedAuroraBackground()
        }

        // 顶部内容区域 (淡出、缩放 + 入场动画)
        Box(
            modifier = Modifier.graphicsLayer {
                this.alpha = contentAlpha * entranceAlpha
                this.scaleX = contentScale
                this.scaleY = contentScale
                this.translationY = entranceTranslationY.toPx()
            }
        ) {
            AboutTopSection()
        }

        // 底部滚动内容
        AboutBottomScrollableContent(
            scrollState = scrollState,
            uriHandler = uriHandler,
            onDeveloperClick = onDeveloperClick,
            onContributorsClick = onContributorsClick,
            onGitHubClick = onGitHubClick,
            onGiteeClick = onGiteeClick,
            onApiDocClick = onApiDocClick,
            onDemoDownloadClick = onDemoDownloadClick,
            onIconSourceClick = onIconSourceClick,
            onGitHubDiscussionClick = onGitHubDiscussionClick,
            onQQGroupClick = onQQGroupClick,
            onWeChatGroupClick = onWeChatGroupClick,
            onTranslationClick = onTranslationClick,
            onSupportClick = onSupportClick,
            onHelpClick = onHelpClick,
            onCitationClick = onCitationClick,
            onUserAgreementClick = onUserAgreementClick,
            onPrivacyPolicyClick = onPrivacyPolicyClick,
            onOpenSourceLicenseClick = onOpenSourceLicenseClick
        )

        // 动画工具栏
        AboutAnimatedToolBar(onBackClick = onBackClick, toolbarAlpha = toolbarAlpha)
    }
}

@Composable
private fun AnimatedAuroraBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "aurora_transition")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "aurora_time"
    )

    val isDarkTheme = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val backgroundColor = MaterialTheme.colorScheme.background

    // 根据亮暗主题定义不同的颜色方案 (参考 HyperCeiler 项目)
    val (color1, color2, color3, color4) = if (isDarkTheme) {
        // 深色主题颜色
        listOf(
            Color(0.0f, 0.31f, 0.58f),   // 暗蓝色
            Color(0.53f, 0.29f, 0.15f),   // 暗橙色/棕色
            Color(0.46f, 0.06f, 0.27f),   // 洋红色
            Color(0.16f, 0.12f, 0.45f)    // 靛蓝色
        )
    } else {
        // 浅色主题颜色
        listOf(
            Color(0.57f, 0.76f, 0.98f), // 亮蓝色
            Color(0.98f, 0.85f, 0.68f), // 亮橙色/黄色
            Color(0.98f, 0.75f, 0.93f), // 亮粉色
            Color(0.73f, 0.7f, 0.98f)  // 亮紫色
        )
    }
    // 第五个光源使用 App 主题色，以突出品牌
    val color5 = MaterialTheme.colorScheme.primary

    // 在深色模式下，极光绘制在黑色画布上以获得更好的鲜艳度
    // 在浅色模式下，直接在背景上绘制
    val topColor = if (isDarkTheme) Color.Black else backgroundColor

    FullScreenBox(
        modifier = Modifier
            .background(topColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(150.dp)
                .drawBehind {
                    val width = size.width
                    val height = size.height

                    // 光源 1
                    val radius1 = width * 0.7f
                    val center1 = Offset(
                        x = width * (0.3f + 0.2f * cos(time)),
                        y = height * (0.4f + 0.2f * sin(time))
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(color1.copy(alpha = 0.9f), Color.Transparent),
                            center = center1,
                            radius = radius1
                        ),
                        radius = radius1,
                        center = center1
                    )

                    // 光源 2
                    val radius2 = width * 0.6f
                    val center2 = Offset(
                        x = width * (0.7f + 0.2f * cos(time * 1.5f + PI.toFloat())),
                        y = height * (0.6f + 0.15f * sin(time * 1.5f + PI.toFloat()))
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(color2.copy(alpha = 0.9f), Color.Transparent),
                            center = center2,
                            radius = radius2
                        ),
                        radius = radius2,
                        center = center2
                    )

                    // 光源 3
                    val radius3 = width * 0.8f
                    val center3 = Offset(
                        x = width * (0.6f + 0.2f * cos(time * 0.8f + PI.toFloat() / 2)),
                        y = height * (0.3f + 0.2f * sin(time * 0.8f + PI.toFloat() / 2))
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(color3.copy(alpha = 0.85f), Color.Transparent),
                            center = center3,
                            radius = radius3
                        ),
                        radius = radius3,
                        center = center3
                    )

                    // 光源 4
                    val radius4 = width * 0.5f
                    val center4 = Offset(
                        x = width * (0.2f + 0.1f * cos(time * 1.2f + PI.toFloat() / 4)),
                        y = height * (0.7f + 0.1f * sin(time * 1.2f + PI.toFloat() / 4))
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(color4.copy(alpha = 0.9f), Color.Transparent),
                            center = center4,
                            radius = radius4
                        ),
                        radius = radius4,
                        center = center4
                    )

                    // 光源 5
                    val radius5 = width * 0.7f
                    val center5 = Offset(
                        x = width * (0.8f + 0.15f * cos(time * 0.9f + PI.toFloat() * 1.5f)),
                        y = height * (0.2f + 0.15f * sin(time * 0.9f + PI.toFloat() * 1.5f))
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(color5.copy(alpha = 0.9f), Color.Transparent),
                            center = center5,
                            radius = radius5
                        ),
                        radius = radius5,
                        center = center5
                    )
                }
        )

        // 渐变遮罩，用于平滑过渡到页面背景
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.1f to Color.Transparent,
                            1.0f to backgroundColor
                        )
                    )
                )
        )
    }
}

/**
 * 顶部内容区域
 *
 * @author Joker.X
 */
@Composable
private fun AboutTopSection() {
    val context = LocalContext.current
    CenterColumn {
        // 顶部占位间距，用于调整Logo的垂直位置
        Spacer(modifier = Modifier.height(130.dp))

        // 应用图标/Logo区域
        Card(
            modifier = Modifier.size(120.dp),
            shape = ShapeExtraLarge,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LogoIcon(size = 100.dp)
            }
        }

        SpaceVerticalXLarge()

        AppText(
            text = PackageUtils.getCurrentAppName(context),
            size = TextSize.DISPLAY_LARGE,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        SpaceVerticalSmall()

        AppText(
            text = "Version ${PackageUtils.getCurrentVersionName(context)}",
            size = TextSize.BODY_MEDIUM,
            type = TextType.TERTIARY
        )
    }
}

/**
 * 底部可滚动内容区域
 *
 * @param scrollState 滚动状态
 * @param uriHandler URI处理器，用于打开外部链接
 * @param onDeveloperClick 点击开发者信息回调
 * @param onContributorsClick 点击贡献者列表回调
 * @param onGitHubClick 点击GitHub项目地址回调
 * @param onGiteeClick 点击Gitee项目地址回调
 * @param onApiDocClick 点击API文档回调
 * @param onDemoDownloadClick 点击Demo下载回调
 * @param onIconSourceClick 点击图标来源回调
 * @param onGitHubDiscussionClick 点击GitHub讨论区回调
 * @param onQQGroupClick 点击QQ交流群回调
 * @param onWeChatGroupClick 点击微信交流群回调
 * @param onTranslationClick 点击翻译帮助回调
 * @param onSupportClick 点击支持项目回调
 * @param onHelpClick 点击帮助与反馈回调
 * @param onCitationClick 点击引用致谢回调
 * @param onUserAgreementClick 点击用户协议回调
 * @param onPrivacyPolicyClick 点击隐私政策回调
 * @param onOpenSourceLicenseClick 点击开源许可回调
 * @author Joker.X
 */
@Composable
private fun AboutBottomScrollableContent(
    scrollState: ScrollState,
    uriHandler: androidx.compose.ui.platform.UriHandler,
    onDeveloperClick: () -> Unit,
    onContributorsClick: () -> Unit,
    onGitHubClick: () -> Unit,
    onGiteeClick: () -> Unit,
    onApiDocClick: () -> Unit,
    onDemoDownloadClick: () -> Unit,
    onIconSourceClick: () -> Unit,
    onGitHubDiscussionClick: () -> Unit,
    onQQGroupClick: () -> Unit,
    onWeChatGroupClick: () -> Unit,
    onTranslationClick: () -> Unit,
    onSupportClick: () -> Unit,
    onHelpClick: () -> Unit,
    onCitationClick: () -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onOpenSourceLicenseClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .padding(horizontal = SpacePaddingLarge)
    ) {
        // 顶部占位间距，使内容从动态背景下方开始
        Spacer(modifier = Modifier.height(360.dp))

        // 应用详情卡片（不可点击）
        Card {
            // 应用名称
            AppListItem(
                title = stringResource(id = R.string.about_app_name),
                trailingText = PackageUtils.getCurrentAppName(context),
                showArrow = false,
                showDivider = true,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 版本号
            AppListItem(
                title = stringResource(id = R.string.about_version),
                trailingText = PackageUtils.getCurrentVersionName(context),
                showArrow = false,
                showDivider = true,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 版本代码
            AppListItem(
                title = stringResource(id = R.string.about_version_code),
                trailingText = PackageUtils.getCurrentVersionCode(context).toString(),
                showArrow = false,
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }

        SpaceVerticalXLarge()

        // 开发者部分
        TitleWithLine(stringResource(id = R.string.about_developer))
        SpaceVerticalMedium()

        Card {
            // 开发者信息
            AppListItem(
                title = "",
                leadingContent = {
                    Image(
                        painter = painterResource(id = R.drawable.avatar),
                        contentDescription = stringResource(id = R.string.about_developer_name),
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        modifier = Modifier.padding(start = SpaceHorizontalMedium)
                    ) {
                        AppText(
                            text = stringResource(id = R.string.about_developer_name),
                            size = TextSize.TITLE_LARGE,
                        )
                        AppText(
                            text = stringResource(id = R.string.about_developer_email),
                            type = TextType.TERTIARY,
                            size = TextSize.BODY_MEDIUM,
                        )
                    }
                },
                showArrow = true,
                showDivider = true,
                horizontalPadding = SpaceHorizontalLarge,
                onClick = {
                    onDeveloperClick()
                }
            )
            // 贡献者
            AppListItem(
                title = stringResource(id = R.string.contributors_title),
                showArrow = true,
                showDivider = false,
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge,
                onClick = {
                    onContributorsClick()
                }
            )
        }

        SpaceVerticalXLarge()

        // 项目地址部分
        TitleWithLine(stringResource(id = R.string.about_section_project))
        SpaceVerticalMedium()
        Card {
            // GitHub
            AppListItem(
                title = stringResource(id = R.string.about_github),
                description = stringResource(id = R.string.about_github_url),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onGitHubClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // Gitee
            AppListItem(
                title = stringResource(id = R.string.about_gitee),
                description = stringResource(id = R.string.about_gitee_url),
                showArrow = true,
                showDivider = false,
                onClick = {
                    onGiteeClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }
        SpaceVerticalXLarge()

        // 相关资源部分
        TitleWithLine(stringResource(id = R.string.about_section_resources))
        SpaceVerticalMedium()
        Card {
            // API 文档
            AppListItem(
                title = stringResource(id = R.string.about_api_doc),
                description = stringResource(id = R.string.about_api_doc_desc),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onApiDocClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // Demo 下载
            AppListItem(
                title = stringResource(id = R.string.about_demo_download),
                description = stringResource(id = R.string.about_demo_download_desc),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onDemoDownloadClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 图标来源
            AppListItem(
                title = stringResource(id = R.string.about_icon_source),
                description = stringResource(id = R.string.about_icon_source_desc),
                showArrow = true,
                showDivider = false,
                onClick = {
                    onIconSourceClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }
        SpaceVerticalXLarge()

        // 讨论部分
        TitleWithLine(stringResource(id = R.string.about_section_discussion))
        SpaceVerticalMedium()
        Card {
            // GitHub 讨论区
            AppListItem(
                title = stringResource(id = R.string.about_github_discussion),
                description = stringResource(id = R.string.about_github_discussion_desc),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onGitHubDiscussionClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // QQ 交流群
            AppListItem(
                title = stringResource(id = R.string.about_qq_group),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onQQGroupClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 微信交流群
            AppListItem(
                title = stringResource(id = R.string.about_wechat_group),
                showArrow = true,
                showDivider = false,
                onClick = {
                    onWeChatGroupClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }
        SpaceVerticalXLarge()

        // 支持与帮助部分
        TitleWithLine(stringResource(id = R.string.about_section_support))
        SpaceVerticalMedium()
        Card {
            // 翻译
            AppListItem(
                title = stringResource(id = R.string.about_translation),
                description = stringResource(id = R.string.about_translation_desc),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onTranslationClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 支持
            AppListItem(
                title = stringResource(id = R.string.about_support),
                description = stringResource(id = R.string.about_support_desc),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onSupportClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 帮助
            AppListItem(
                title = stringResource(id = R.string.about_help),
                description = stringResource(id = R.string.about_help_desc),
                showArrow = true,
                showDivider = false,
                onClick = {
                    onHelpClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }
        SpaceVerticalXLarge()

        // 其他部分
        TitleWithLine(stringResource(id = R.string.about_section_other))
        SpaceVerticalMedium()
        Card {
            // 引用
            AppListItem(
                title = stringResource(id = R.string.about_citation),
                description = stringResource(id = R.string.about_citation_desc),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onCitationClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 用户协议
            AppListItem(
                title = stringResource(id = R.string.about_user_agreement),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onUserAgreementClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 隐私政策
            AppListItem(
                title = stringResource(id = R.string.about_privacy_policy),
                showArrow = true,
                showDivider = true,
                onClick = {
                    onPrivacyPolicyClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
            // 开源许可
            AppListItem(
                title = stringResource(id = R.string.about_open_source_license),
                showArrow = true,
                showDivider = false,
                onClick = {
                    onOpenSourceLicenseClick()
                },
                horizontalPadding = SpaceHorizontalLarge,
                verticalPadding = SpaceVerticalLarge
            )
        }
        SpaceVerticalXLarge()

        AppText(
            text = stringResource(id = R.string.about_copyright),
            size = TextSize.BODY_MEDIUM,
            type = TextType.TERTIARY,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SpacePaddingLarge)
        )
    }
}

/**
 * 动画工具栏
 *
 * @param onBackClick 返回按钮回调
 * @param toolbarAlpha 工具栏透明度
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutAnimatedToolBar(
    onBackClick: () -> Unit,
    toolbarAlpha: Float
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.about_title),
                modifier = Modifier.alpha(toolbarAlpha),
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                ArrowLeftIcon()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background.copy(
                alpha = toolbarAlpha
            ),
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        scrollBehavior = scrollBehavior
    )
}

/**
 * 关于我们界面浅色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun AboutScreenPreview() {
    AppTheme {
        AboutScreen()
    }
}

/**
 * 关于我们界面深色主题预览
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
internal fun AboutScreenPreviewDark() {
    AppTheme(darkTheme = true) {
        AboutScreen()
    }
}