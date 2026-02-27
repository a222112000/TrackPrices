package com.alselwi.trackprices.domain.usecase

import com.alselwi.trackprices.domain.repository.StockRepository

class StopFeedUseCase(private val repository: StockRepository) {
    operator fun invoke() = repository.stop()
}