package com.joker.coolmall.feature.auth.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.joker.coolmall.navigation.auth.AuthRoutes
import com.joker.coolmall.feature.auth.view.AccountLoginRoute
import com.joker.coolmall.feature.auth.view.LoginRoute
import com.joker.coolmall.feature.auth.view.RegisterRoute
import com.joker.coolmall.feature.auth.view.ResetPasswordRoute
import com.joker.coolmall.feature.auth.view.SmsLoginRoute

/**
 * 认证模块导航图
 *
 * @author Joker.X
 */
fun EntryProviderScope<NavKey>.authGraph() {
    entry<AuthRoutes.Login> {
        LoginRoute()
    }
    entry<AuthRoutes.AccountLogin> {
        AccountLoginRoute()
    }
    entry<AuthRoutes.SmsLogin> {
        SmsLoginRoute()
    }
    entry<AuthRoutes.Register> {
        RegisterRoute()
    }
    entry<AuthRoutes.ResetPassword> {
        ResetPasswordRoute()
    }
}
