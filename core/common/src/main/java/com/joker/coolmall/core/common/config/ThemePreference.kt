package com.joker.coolmall.core.common.config

/**
 * 应用主题偏好
 *
 * @property storageValue 存储到本地时使用的字符串
 * @author Joker.X
 */
enum class ThemePreference(val storageValue: String) {
    FOLLOW_SYSTEM("follow_system"),
    LIGHT("light"),
    DARK("dark");

    companion object {
        /**
         * 将存储值转换为枚举，未知值时返回跟随系统
         *
         * @author Joker.X
         */
        fun fromStorage(value: String?): ThemePreference {
            return entries.firstOrNull { it.storageValue == value } ?: FOLLOW_SYSTEM
        }
    }
}
