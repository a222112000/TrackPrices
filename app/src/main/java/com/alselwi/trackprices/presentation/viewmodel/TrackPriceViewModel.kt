package com.alselwi.trackprices.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.alselwi.trackprices.domain.usecase.ObserveConnectionUseCase
import com.alselwi.trackprices.domain.usecase.ObserveStockUseCase
import com.alselwi.trackprices.domain.usecase.StartFeedUseCase
import com.alselwi.trackprices.domain.usecase.StopFeedUseCase
import com.alselwi.trackprices.presentation.base.BaseViewModel
import com.alselwi.trackprices.presentation.contract.FeedContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TrackPriceViewModel(
    private val observeStocks: ObserveStockUseCase,
    private val observeConnection: ObserveConnectionUseCase,
    private val startFeed: StartFeedUseCase,
    private val stopFeed: StopFeedUseCase
) : BaseViewModel<FeedContract.UiState, FeedContract.Event, FeedContract.Effect>(FeedContract.UiState()){
    override fun handleEvent(event: FeedContract.Event) {
        when(event){
            is FeedContract.Event.SymbolClicked -> {
                setEffect {
                    FeedContract.Effect.NavigateToDetails(event.symbol)
                }
            }
            FeedContract.Event.ToggleFeed -> toggleFeed()
        }
    }

    private fun toggleFeed(){
        viewModelScope.launch {
            val running = state.value.isRunning
            setState{copy(isLoading = true)}
            try {
                if(running) stopFeed() else startFeed()
                setState { copy(isRunning = !running, isLoading = false) }
            }catch (e: Exception){
                setState { copy(isLoading = false) }
                setEffect { FeedContract.Effect.ShowError("Error Occurred") }
            }
        }
    }

    init {
        obServeData()
    }
    private fun obServeData(){
        observeStocks()
            .onEach { stocks->
                setState{copy(stocks = stocks)}
            }.launchIn(viewModelScope)
        observeConnection()
            .onEach { connected->
                setState { copy(isConnected = connected) }
            }.launchIn(viewModelScope)
    }

}