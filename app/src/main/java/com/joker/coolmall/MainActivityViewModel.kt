package com.joker.coolmall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joker.coolmall.core.data.repository.GoodsRepository
import com.joker.coolmall.core.result.Result
import com.joker.coolmall.core.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 主界面 ViewModel
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private var goodsRepository: GoodsRepository
) : ViewModel() {

    fun getTest() {
        Timber.w("获取商品列表")
        viewModelScope.launch {
            goodsRepository.getGoods()
                .asResult()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            Timber.d("商品列表加载中...")
                        }

                        is Result.Success -> {
                            val goods = result.data.list ?: emptyList()
                            Timber.d("商品列表加载成功: 共${goods.size}件商品")
                        }

                        is Result.Error -> {
                            Timber.e(result.exception, "商品列表加载失败")
                        }
                    }
                }
        }
    }
}