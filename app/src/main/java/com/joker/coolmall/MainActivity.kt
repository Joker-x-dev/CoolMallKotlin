package com.joker.coolmall

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.joker.coolmall.core.common.config.ThemePreference
import com.joker.coolmall.core.common.manager.ThemePreferenceManager
import com.joker.coolmall.core.common.manager.QQLoginManager
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.navigation.AppNavHost
import com.joker.coolmall.navigation.AppNavigator
import com.tencent.connect.common.Constants
import com.tencent.tauth.Tencent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 应用的主Activity
 * 使用@AndroidEntryPoint注解标记为Hilt依赖注入的入口点
 *
 * @author Joker.X
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        // 启动页
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        // 启用边缘到边缘的显示效果
        enableEdgeToEdge()
        // 设置Compose内容
        setContent {
            val themeMode by ThemePreferenceManager.themeMode.collectAsState()
            val themeColorOption by ThemePreferenceManager.themeColor.collectAsState()
            val isDarkTheme = when (themeMode) {
                ThemePreference.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                ThemePreference.LIGHT -> false
                ThemePreference.DARK -> true
            }
            val primaryColor = remember(themeColorOption) {
                Color(themeColorOption.colorHex)
            }
            // 应用主题包装
            AppTheme(
                darkTheme = isDarkTheme,
                themeColor = primaryColor
            ) {
                // 设置应用的导航宿主，并传入导航管理器和路由注册器
                // 这样所有页面都可以通过导航管理器进行导航操作
                AppNavHost(navigator = navigator)
            }
        }

        // 不让启动界面一直显示
        splashScreen.setKeepOnScreenCondition {
            false
        }
    }

    /**
     * 处理 Activity 结果回调，目前主要用于三方登录
     *
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 返回数据
     * @author Joker.X
     */
    @Deprecated("")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 处理 QQ 登录回调
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(
                requestCode,
                resultCode,
                data,
                QQLoginManager.getInstance().qqLoginListener
            )
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
