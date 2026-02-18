package com.joker.coolmall.feature.common.viewmodel

import com.joker.coolmall.core.common.base.viewmodel.BaseViewModel
import com.joker.coolmall.navigation.common.CommonNavigator
import com.joker.coolmall.feature.common.data.DependencyDataSource
import com.joker.coolmall.feature.common.model.Dependency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * 关于我们 ViewModel
 *
 * @author Joker.X
 */
@HiltViewModel
class AboutViewModel @Inject constructor() : BaseViewModel() {

    /**
     * 依赖弹出层显示状态
     */
    private val _showDependencyModal = MutableStateFlow(false)
    val showDependencyModal: StateFlow<Boolean> = _showDependencyModal.asStateFlow()

    /**
     * 依赖列表数据
     */
    val dependencies: List<Dependency> = DependencyDataSource.getAllDependencies()

    /**
     * 点击引用致谢
     * 显示项目中使用的第三方库和资源致谢
     *
     * @author Joker.X
     */
    fun onCitationClick() {
        _showDependencyModal.value = true
    }

    /**
     * 关闭依赖弹出层
     *
     * @author Joker.X
     */
    fun onDismissDependencyModal() {
        _showDependencyModal.value = false
    }

    /**
     * 点击依赖项
     * 打开依赖的官方网站
     *
     * @param dependency 依赖对象
     * @author Joker.X
     */
    fun onDependencyClick(dependency: Dependency) {
        if (dependency.websiteUrl.isNotEmpty()) {
            CommonNavigator.toWeb(url = dependency.websiteUrl, title = dependency.name)
        }
        // 点击后关闭弹出层
        _showDependencyModal.value = false
    }
}