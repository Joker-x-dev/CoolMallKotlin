package com.joker.coolmall.feature.common.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.joker.coolmall.core.navigation.common.CommonRoutes
import com.joker.coolmall.feature.common.view.AboutRoute
import com.joker.coolmall.feature.common.view.ContributorsRoute
import com.joker.coolmall.feature.common.view.PrivacyPolicyRoute
import com.joker.coolmall.feature.common.view.SettingsRoute
import com.joker.coolmall.feature.common.view.UserAgreementRoute
import com.joker.coolmall.feature.common.view.WebRoute

/**
 * 通用模块导航图
 *
 * @author Joker.X
 */
fun EntryProviderScope<NavKey>.commonGraph() {
    entry<CommonRoutes.About> {
        AboutRoute()
    }
    entry<CommonRoutes.Web> { key ->
        WebRoute(navKey = key)
    }
    entry<CommonRoutes.Settings> {
        SettingsRoute()
    }
    entry<CommonRoutes.UserAgreement> {
        UserAgreementRoute()
    }
    entry<CommonRoutes.PrivacyPolicy> {
        PrivacyPolicyRoute()
    }
    entry<CommonRoutes.Contributors> {
        ContributorsRoute()
    }
}
