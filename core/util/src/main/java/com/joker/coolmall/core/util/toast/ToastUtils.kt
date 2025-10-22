package com.joker.coolmall.core.util.toast

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import com.hjq.toast.ToastParams
import com.hjq.toast.Toaster
import com.hjq.toast.style.BlackToastStyle
import com.hjq.toast.style.CustomToastStyle
import com.hjq.toast.style.WhiteToastStyle
import com.joker.coolmall.core.util.R

/**
 * Toast 工具类，基于 Toaster 框架封装
 * 提供基本的 Toast 显示和特定样式（成功、失败、警告）
 *
 * @author Joker.X
 */
object ToastUtils {

    /**
     * 是否为深色主题
     */
    private var isDarkMode = false

    /**
     * 初始化 Toast，应在 Application 中调用
     * 用法示例：ToastUtils.init(application, isDarkTheme)
     *
     * @param application Application 对象
     * @param isDarkTheme 是否为深色主题，用于选择默认样式
     * @author Joker.X
     */
    fun init(application: Application, isDarkTheme: Boolean = false) {
        // 保存当前主题模式
        isDarkMode = isDarkTheme

        // 根据主题选择默认样式
        val style = if (isDarkTheme) WhiteToastStyle() else BlackToastStyle()
        Toaster.init(application, style)
    }

    /**
     * 设置为黑色样式
     * 用法示例：ToastUtils.setBlackStyle()
     *
     * @author Joker.X
     */
    fun setBlackStyle() {
        isDarkMode = false
        Toaster.setStyle(BlackToastStyle())
    }

    /**
     * 设置为白色样式
     * 用法示例：ToastUtils.setWhiteStyle()
     *
     * @author Joker.X
     */
    fun setWhiteStyle() {
        isDarkMode = true
        Toaster.setStyle(WhiteToastStyle())
    }

    /**
     * 显示普通 Toast
     * 用法示例：ToastUtils.show("这是普通提示")
     *
     * @param text 文本内容
     * @author Joker.X
     */
    fun show(text: CharSequence) {
        Toaster.show(text)
    }

    /**
     * 显示普通 Toast（资源ID）
     * 用法示例：ToastUtils.show(R.string.message)
     *
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun show(@StringRes resId: Int) {
        Toaster.show(resId)
    }

    /**
     * 显示成功样式的 Toast
     * 用法示例：ToastUtils.showSuccess("操作成功")
     *
     * @param text 文本内容
     * @author Joker.X
     */
    fun showSuccess(text: CharSequence) {
        val params = ToastParams()
        params.text = text
        params.style = CustomToastStyle(R.layout.toast_success)
        Toaster.show(params)
    }

    /**
     * 显示成功样式的 Toast（资源ID）
     * 用法示例：ToastUtils.showSuccess(context, R.string.success_message)
     *
     * @param context 上下文
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun showSuccess(context: Context, @StringRes resId: Int) {
        val text = context.getString(resId)
        showSuccess(text)
    }

    /**
     * 显示成功样式的 Toast（资源ID），不需要传递上下文
     * 注意：此方法仅适用于Toast已经初始化之后使用
     * 用法示例：ToastUtils.showSuccess(R.string.success_message)
     *
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun showSuccess(@StringRes resId: Int) {
        // 保存当前样式
        val currentStyle = if (isDarkMode) WhiteToastStyle() else BlackToastStyle()

        // 设置成功样式
        Toaster.setStyle(CustomToastStyle(R.layout.toast_success))
        Toaster.show(resId)

        // 恢复默认样式
        Toaster.setStyle(currentStyle)
    }

    /**
     * 显示失败样式的 Toast
     * 用法示例：ToastUtils.showError("操作失败")
     *
     * @param text 文本内容
     * @author Joker.X
     */
    fun showError(text: CharSequence) {
        val params = ToastParams()
        params.text = text
        params.style = CustomToastStyle(R.layout.toast_error)
        Toaster.show(params)
    }

    /**
     * 显示失败样式的 Toast（资源ID）
     * 用法示例：ToastUtils.showError(context, R.string.error_message)
     *
     * @param context 上下文
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun showError(context: Context, @StringRes resId: Int) {
        val text = context.getString(resId)
        showError(text)
    }

    /**
     * 显示失败样式的 Toast（资源ID），不需要传递上下文
     * 注意：此方法仅适用于Toast已经初始化之后使用
     * 用法示例：ToastUtils.showError(R.string.error_message)
     *
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun showError(@StringRes resId: Int) {
        // 保存当前样式
        val currentStyle = if (isDarkMode) WhiteToastStyle() else BlackToastStyle()

        // 设置错误样式
        Toaster.setStyle(CustomToastStyle(R.layout.toast_error))
        Toaster.show(resId)

        // 恢复默认样式
        Toaster.setStyle(currentStyle)
    }

    /**
     * 显示警告样式的 Toast
     * 用法示例：ToastUtils.showWarning("请注意")
     *
     * @param text 文本内容
     * @author Joker.X
     */
    fun showWarning(text: CharSequence) {
        val params = ToastParams()
        params.text = text
        params.style = CustomToastStyle(R.layout.toast_warn)
        Toaster.show(params)
    }

    /**
     * 显示警告样式的 Toast（资源ID）
     * 用法示例：ToastUtils.showWarning(context, R.string.warning_message)
     *
     * @param context 上下文
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun showWarning(context: Context, @StringRes resId: Int) {
        val text = context.getString(resId)
        showWarning(text)
    }

    /**
     * 显示警告样式的 Toast（资源ID），不需要传递上下文
     * 注意：此方法仅适用于Toast已经初始化之后使用
     * 用法示例：ToastUtils.showWarning(R.string.warning_message)
     *
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun showWarning(@StringRes resId: Int) {
        // 保存当前样式
        val currentStyle = if (isDarkMode) WhiteToastStyle() else BlackToastStyle()

        // 设置警告样式
        Toaster.setStyle(CustomToastStyle(R.layout.toast_warn))
        Toaster.show(resId)

        // 恢复默认样式
        Toaster.setStyle(currentStyle)
    }

    /**
     * 显示短时间Toast
     * 用法示例：ToastUtils.showShort("这是短时间提示")
     *
     * @param text 文本内容
     * @author Joker.X
     */
    fun showShort(text: CharSequence) {
        Toaster.showShort(text)
    }

    /**
     * 显示短时间Toast（资源ID）
     * 用法示例：ToastUtils.showShort(R.string.short_message)
     *
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun showShort(@StringRes resId: Int) {
        Toaster.showShort(resId)
    }

    /**
     * 显示长时间Toast
     * 用法示例：ToastUtils.showLong("这是长时间提示")
     *
     * @param text 文本内容
     * @author Joker.X
     */
    fun showLong(text: CharSequence) {
        Toaster.showLong(text)
    }

    /**
     * 显示长时间Toast（资源ID）
     * 用法示例：ToastUtils.showLong(R.string.long_message)
     *
     * @param resId 字符串资源ID
     * @author Joker.X
     */
    fun showLong(@StringRes resId: Int) {
        Toaster.showLong(resId)
    }

    /**
     * 延迟显示Toast
     * 用法示例：ToastUtils.delayedShow("这是延迟显示的提示", 2000)
     *
     * @param text 文本内容
     * @param delayMillis 延迟时间，单位毫秒
     * @author Joker.X
     */
    fun delayedShow(text: CharSequence, delayMillis: Long) {
        Toaster.delayedShow(text, delayMillis)
    }

    /**
     * 延迟显示Toast（资源ID）
     * 用法示例：ToastUtils.delayedShow(R.string.delayed_message, 2000)
     *
     * @param resId 字符串资源ID
     * @param delayMillis 延迟时间，单位毫秒
     * @author Joker.X
     */
    fun delayedShow(@StringRes resId: Int, delayMillis: Long) {
        Toaster.delayedShow(resId, delayMillis)
    }

    /**
     * 取消Toast显示
     * 用法示例：ToastUtils.cancel()
     *
     * @author Joker.X
     */
    fun cancel() {
        Toaster.cancel()
    }
}