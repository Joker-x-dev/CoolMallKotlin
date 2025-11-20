package com.joker.coolmall.core.common.manager

import com.joker.coolmall.core.common.config.ThemeColorOption
import com.joker.coolmall.core.common.config.ThemePreference
import com.joker.coolmall.core.util.storage.MMKVUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 主题偏好管理器
 *
 * 负责从 MMKV 读取 / 写入主题设置，并通过 StateFlow 暴露给 UI 订阅。
 * 这样可以在任意位置实时监听主题变更，刷新界面。
 *
 * @author Joker.X
 */
object ThemePreferenceManager {

    /**
     * 主题模式 MMKV 存储键
     */
    private const val KEY_THEME_MODE = "settings_theme_mode"

    /**
     * 主题颜色 MMKV 存储键
     */
    private const val KEY_THEME_COLOR = "settings_theme_color"

    /**
     * 主题模式
     */
    private val _themeMode = MutableStateFlow(readThemeMode())
    val themeMode: StateFlow<ThemePreference> = _themeMode.asStateFlow()

    /**
     * 主题颜色
     */
    private val _themeColor = MutableStateFlow(readThemeColor())
    val themeColor: StateFlow<ThemeColorOption> = _themeColor.asStateFlow()

    /**
     * 获取当前主题模式
     */
    val currentThemeMode: ThemePreference
        get() = _themeMode.value

    /**
     * 获取当前主题颜色
     */
    val currentThemeColor: ThemeColorOption
        get() = _themeColor.value

    /**
     * 更新主题模式，同时写入 MMKV
     * @param mode 新的主题模式
     *
     * @author Joker.X
     */
    fun updateThemeMode(mode: ThemePreference) {
        if (_themeMode.value == mode) return
        _themeMode.value = mode
        runCatching {
            MMKVUtils.putString(KEY_THEME_MODE, mode.storageValue)
        }
    }

    /**
     * 更新主题颜色
     * @param option 新的主题颜色选项
     *
     * @author Joker.X
     */
    fun updateThemeColor(option: ThemeColorOption) {
        if (_themeColor.value == option) return
        _themeColor.value = option
        runCatching {
            MMKVUtils.putString(KEY_THEME_COLOR, option.storageValue)
        }
    }

    /**
     * 从本地读取主题配置，异常时回退为跟随系统
     *
     * @return 读取到的主题模式
     * @author Joker.X
     */
    private fun readThemeMode(): ThemePreference {
        val storedValue = runCatching {
            MMKVUtils.getString(KEY_THEME_MODE, ThemePreference.FOLLOW_SYSTEM.storageValue)
        }.getOrDefault(ThemePreference.FOLLOW_SYSTEM.storageValue)
        return ThemePreference.fromStorage(storedValue)
    }

    /**
     * 读取主题颜色配置
     *
     * @return 读取到的主题颜色选项
     * @author Joker.X
     */
    private fun readThemeColor(): ThemeColorOption {
        val storedValue = runCatching {
            MMKVUtils.getString(KEY_THEME_COLOR, ThemeColorOption.DEFAULT.storageValue)
        }.getOrDefault(ThemeColorOption.DEFAULT.storageValue)
        return ThemeColorOption.fromStorage(storedValue)
    }
}
