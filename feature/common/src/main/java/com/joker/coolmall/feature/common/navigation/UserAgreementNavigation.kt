package com.joker.coolmall.feature.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joker.coolmall.feature.common.view.UserAgreementRoute
import com.joker.coolmall.navigation.routes.CommonRoutes

/**
 * 用户协议页面导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.userAgreementScreen() {
    composable<CommonRoutes.UserAgreement> {
        UserAgreementRoute()
    }
}
