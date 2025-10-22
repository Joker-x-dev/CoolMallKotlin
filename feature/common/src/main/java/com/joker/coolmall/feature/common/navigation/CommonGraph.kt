package com.joker.coolmall.feature.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * 通用模块导航图
 *
 * 通用模块下所有页面的导航
 *
 * @author Joker.X
 */
fun NavGraphBuilder.commonGraph(navController: NavHostController) {
    aboutScreen()
    webScreen()
    settingsScreen()
    userAgreementScreen()
    privacyPolicyScreen()
    contributorsScreen()
}