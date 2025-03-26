package com.joker.coolmall.feature.main.state

import com.joker.coolmall.core.model.Banner

sealed interface HomeUiState {
    data class Success(
        val data: Banner,
    ) : HomeUiState
}