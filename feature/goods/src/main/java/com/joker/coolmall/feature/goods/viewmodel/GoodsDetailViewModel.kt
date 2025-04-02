package com.joker.coolmall.feature.goods.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjq.toast.Toaster
import com.joker.coolmall.core.util.toast.ToastUtils
import com.joker.coolmall.feature.goods.navigation.GoodsDetailRoutes
import com.joker.coolmall.navigation.AppNavigator
import com.joker.coolmall.feature.goods.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 商品详情页面ViewModel
 */
@HiltViewModel
class GoodsDetailViewModel @Inject constructor(
    private val navigator: AppNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 从SavedStateHandle获取路由参数
    private val goodsId: Long = checkNotNull(savedStateHandle[GoodsDetailRoutes.GOODS_ID_ARG])

    // 商品详情状态
    private val _uiState = MutableStateFlow(GoodsDetailUiState())
    val uiState: StateFlow<GoodsDetailUiState> = _uiState.asStateFlow()

    init {
//        ToastUtils.showWarning(com.joker.coolmall.core.ui.R.string.empty_cart)
        // 初始化时加载商品数据
        loadGoodsDetails()
    }

    /**
     * 加载商品详情
     */
    private fun loadGoodsDetails() {
        // 更新UI状态为加载中
        _uiState.value = GoodsDetailUiState(
            isLoading = true,
            goodsId = goodsId.toString()
        )

        // 模拟网络请求
        viewModelScope.launch {
            // 模拟延迟
            delay(1000)

            try {
                // 模拟获取商品详情数据
                val mockGoods = getMockGoodsDetail(goodsId)
                
                // 更新UI状态为成功
                _uiState.value = GoodsDetailUiState(
                    isLoading = false,
                    goodsId = mockGoods.id,
                    goodsName = mockGoods.title,
                    goodsSubTitle = mockGoods.subTitle,
                    goodsPrice = "¥${mockGoods.price}",
                    goodsMainPic = mockGoods.mainPic,
                    goodsPics = mockGoods.pics,
                    goodsContent = mockGoods.content
                )
            } catch (e: Exception) {
                // 更新UI状态为错误
                _uiState.value = GoodsDetailUiState(
                    isLoading = false,
                    goodsId = goodsId.toString(),
                    error = e.message ?: "未知错误"
                )
            }
        }
    }

    /**
     * 模拟获取商品详情数据
     */
    private fun getMockGoodsDetail(goodsId: Long): MockGoodsDetail {
        // 这里返回固定的模拟数据，实际应该从网络获取
        return MockGoodsDetail(
            id = goodsId.toString(),
            title = "Redmi 14C",
            subTitle = "【持久续航】5160mAh 大电池",
            price = 499,
            mainPic = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffcd84baf3d3a4b49b35a03aaf783281e_%E7%BA%A2%E7%B1%B3%2014c.png",
            pics = listOf(
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F83561ee604b14aae803747c32ff59cbb_b1.png",
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F32051f923ded432c82ef5934451a601b_b2.jpg",
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F88bf37e8c9ce42968067cbf3d717f613_b3.jpg",
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F605b0249e73a4fe185c0a075ee85c7a3_b4.jpeg",
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ffb3679b641214f9b8af929cc58d1fe87_b5.jpeg",
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fd1cbc7c3e2e04aa28ed27b6913dbe05b_b6.jpeg",
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F3c081339d951490b8d232477d9249ec2_b7.jpeg",
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3b7302aa7944f7caad225fb32652999_b8.jpeg",
                "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F54a05d34d02141ee8c05a129a7cb3555_b9.jpeg"
            ),
            content = "<p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F5c161af71062402d8dc7e3193e62d8f5_d1.png\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fea304cef45b846d2b7fc4e7fbef6d103_d2.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff3d17dae77d144b9aa828537f96d04e4_d3.jpg\" alt=\"\" data-href=\"\" style=\"width: 100%;\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F99710ccacd5443518a9b97386d028b5c_d4.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Fa180b572f52142d5811dcf4e18c27a95_d5.jpg\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2Ff5bab785f9d04ac38b35e10a1b63486e_d6.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p><p><img src=\"https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app%2Fbase%2F19f52075481c44a789dcf648e3f8a7aa_d7.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>"
        )
    }

    /**
     * 返回上一页
     */
    fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateBack()
        }
    }
}

/**
 * 模拟商品详情数据类
 */
data class MockGoodsDetail(
    val id: String,
    val title: String,
    val subTitle: String,
    val price: Int,
    val mainPic: String,
    val pics: List<String>,
    val content: String
)

/**
 * 商品详情页面UI状态
 */
data class GoodsDetailUiState(
    val isLoading: Boolean = false,
    val goodsId: String = "",
    val goodsName: String = "",
    val goodsSubTitle: String = "",
    val goodsPrice: String = "",
    val goodsMainPic: String = "",
    val goodsPics: List<String> = emptyList(),
    val goodsContent: String = "",
    val error: String? = null
)