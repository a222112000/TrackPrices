package com.alselwi.trackprices.di

import com.alselwi.trackprices.data.remote.WebSocketManager
import com.alselwi.trackprices.data.repository.StockRepositoryImpl
import com.alselwi.trackprices.domain.repository.StockRepository
import com.alselwi.trackprices.domain.usecase.ObserveConnectionUseCase
import com.alselwi.trackprices.domain.usecase.ObserveStockUseCase
import com.alselwi.trackprices.domain.usecase.StartFeedUseCase
import com.alselwi.trackprices.domain.usecase.StopFeedUseCase
import com.alselwi.trackprices.presentation.viewmodel.TrackPriceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { WebSocketManager() }
    single<StockRepository> { StockRepositoryImpl(get()) }
    factory { ObserveStockUseCase(get()) }
    factory { ObserveConnectionUseCase(get()) }
    factory { StartFeedUseCase(get()) }
    factory { StopFeedUseCase(get()) }
    viewModel { TrackPriceViewModel(get(),get(),get(),get()) }
}