package com.alselwi.trackprices.presentation.contract

import com.alselwi.trackprices.domain.entity.Stock

object FeedContract {
    data class UiState(
        val stocks: List<Stock> = emptyList(),
        val isConnected: Boolean = false,
        val isRunning: Boolean = false,
        val isLoading: Boolean = false
    )

    sealed interface Event{
        data object ToggleFeed : Event
        data class SymbolClicked(val symbol: String): Event
    }

    sealed interface Effect{
        data class NavigateToDetails(val symbol: String): Effect
        data class ShowError(val message: String): Effect
    }
}