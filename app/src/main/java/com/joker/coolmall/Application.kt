package com.joker.coolmall

import android.app.Application
import android.content.res.Configuration
import com.joker.coolmall.core.common.config.AppConfig
import com.joker.coolmall.core.common.manager.QQLoginManager
import com.joker.coolmall.core.data.state.AppState
import com.joker.coolmall.core.designsystem.BuildConfig
import com.joker.coolmall.core.util.log.LogUtils
import com.joker.coolmall.core.util.storage.MMKVUtils
import com.joker.coolmall.core.util.toast.ToastUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * 全局Application
 *
 * @author Joker.X
 */
@HiltAndroidApp
class Application : Application() {

    // 注入全局状态管理器
    @Inject
    lateinit var appState: AppState

    override fun onCreate() {
        super.onCreate()
        initToast()
        initLog()
        initMMKV()
        initQQ()

        // 由于 appState 依赖 MMKV
        // 所以等待 MMKV 初始化完成以后再初始化 AppState
        appState.initialize()
    }

    /**
     * 初始化 Toast 框架
     *
     * @author Joker.X
     */
    private fun initToast() {
        // 检测当前是否为深色模式
        val isDarkTheme = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

        // 初始化Toast，传递深色模式参数
        ToastUtils.init(this, isDarkTheme)
    }

    /**
     * 初始化 Log 框架
     *
     * @author Joker.X
     */
    private fun initLog() {
        LogUtils.init(this, BuildConfig.DEBUG)
    }

    /**
     * 初始化 MMKV 框架
     *
     * @author Joker.X
     */
    private fun initMMKV() {
        MMKVUtils.init(this)
    }

    /**
     * 初始化 QQ SDK
     *
     * @author Joker.X
     */
    private fun initQQ() {
        QQLoginManager.getInstance().init(this, AppConfig.QQ_APP_ID)
    }

    /**
     * 应用配置变化时调用（如切换深色模式）
     *
     * @param newConfig 新的配置信息
     * @author Joker.X
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // 检测深色模式变化并更新Toast样式
        val isDarkTheme = newConfig.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

        // 根据当前主题重新设置Toast样式
        if (isDarkTheme) {
            ToastUtils.setWhiteStyle()
        } else {
            ToastUtils.setBlackStyle()
        }
    }
}