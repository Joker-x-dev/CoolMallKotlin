package com.joker.coolmall

import android.app.Application
import com.hjq.toast.Toaster
import com.joker.coolmall.core.designsystem.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * 全局Application
 */
@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        initToast()
        initLog()
    }

    /**
     * 初始化 Toast 框架
     */
    private fun initToast() {
        Toaster.init(this)
    }

    /**
     * 初始化 Log 框架
     */
    private fun initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
} 