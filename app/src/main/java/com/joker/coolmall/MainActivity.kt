package com.joker.coolmall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.joker.coolmall.core.designsystem.theme.AppTheme
import com.joker.coolmall.navigation.AppNavHost
import com.joker.coolmall.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 应用的主Activity
 * 使用@AndroidEntryPoint注解标记为Hilt依赖注入的入口点
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 启用边缘到边缘的显示效果
        enableEdgeToEdge()
        // 设置Compose内容
        setContent {
            // 应用主题包装
            AppTheme {
                // 设置应用的导航宿主，并传入导航管理器和路由注册器
                // 这样所有页面都可以通过导航管理器进行导航操作
                AppNavHost(navigator = navigator)
            }
        }
    }
}
