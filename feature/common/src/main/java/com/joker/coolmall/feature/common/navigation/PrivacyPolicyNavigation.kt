package com.joker.coolmall.feature.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.common.view.PrivacyPolicyRoute
import com.joker.coolmall.navigation.routes.CommonRoutes

/**
 * 隐私政策页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.privacyPolicyScreen() {
    composable<CommonRoutes.PrivacyPolicy> {
        PrivacyPolicyRoute()
    }
}
