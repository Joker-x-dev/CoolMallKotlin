package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.feature.common.model.LinkCategory
import com.joker.coolmall.feature.common.model.LinkItem
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 关于我们 ViewModel
 */
@HiltViewModel
class AboutViewModel @Inject constructor(
    navigator: AppNavigator,
    appState: AppState,
) : BaseViewModel(navigator, appState) {

    private val _appDetails = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val appDetails: StateFlow<List<Pair<String, String>>> = _appDetails.asStateFlow()

    private val _linkCategories = MutableStateFlow<List<LinkCategory>>(emptyList())
    val linkCategories: StateFlow<List<LinkCategory>> = _linkCategories.asStateFlow()

    init {
        loadInfo()
    }

    /**
     * 加载关于页面的所有信息
     */
    private fun loadInfo() {
        _appDetails.value = listOf(
            "应用名称" to "青商城",
            "版本号" to "1.0.0",
            "版本代码" to "2025070701",
        )

        _linkCategories.value = listOf(
            LinkCategory(
                title = "项目地址",
                links = listOf(
                    LinkItem(
                        title = "GitHub",
                        url = "https://github.com/Joker-x-dev/CoolMallKotlin",
                    ),
                    LinkItem(title = "Gitee", url = "https://gitee.com/Joker-x-dev/CoolMallKotlin"),
                )
            ),
            LinkCategory(
                title = "相关资源",
                links = listOf(
                    LinkItem(
                        title = "API 文档",
                        url = "https://coolmall.apifox.cn",
                        description = "查看项目后端接口的详细定义"
                    ),
                    LinkItem(
                        title = "Demo 下载",
                        url = "https://www.pgyer.com/CoolMallKotlinProdRelease",
                        description = "下载最新的安卓应用安装包进行体验"
                    ),
                    LinkItem(
                        title = "图标来源",
                        url = "https://github.com/tuniaoTech",
                        description = "项目使用的图标库来自图鸟 Icon"
                    )
                )
            ),
            LinkCategory(
                title = "讨论",
                links = listOf(
                    LinkItem(
                        title = "GitHub 讨论区",
                        url = "https://github.com/Joker-x-dev/CoolMallKotlin/discussions",
                        description = "在 GitHub 上参与项目讨论"
                    ),
                    LinkItem(
                        title = "QQ 交流群",
                        url = "",
                    ),
                    LinkItem(
                        title = "微信交流群",
                        url = "",
                    )
                )
            ),
            LinkCategory(
                title = "支持与帮助",
                links = listOf(
                    LinkItem(
                        title = "翻译",
                        url = "https://github.com/Joker-x-dev/CoolMallKotlin/issues",
                        description = "帮助我将应用翻译为您的语言"
                    ),
                    LinkItem(
                        title = "支持",
                        url = "https://github.com/Joker-x-dev/CoolMallKotlin",
                        description = "您可以在此赞助以支持我"
                    ),
                    LinkItem(
                        title = "帮助",
                        url = "https://github.com/Joker-x-dev/CoolMallKotlin/issues",
                        description = "有任何问题或建议，欢迎提交 Issue"
                    )
                )
            ),
            LinkCategory(
                title = "其他",
                links = listOf(
                    LinkItem(
                        title = "引用",
                        url = "",
                        description = "项目中使用的第三方库和资源致谢"
                    ),
                    LinkItem(
                        title = "用户协议",
                        url = "",
                    ),
                    LinkItem(
                        title = "隐私政策",
                        url = "",
                    ),
                    LinkItem(
                        title = "开源许可",
                        url = "https://github.com/Joker-x-dev/CoolMallKotlin/blob/main/LICENSE",
                    )
                )
            )
        )
    }
}