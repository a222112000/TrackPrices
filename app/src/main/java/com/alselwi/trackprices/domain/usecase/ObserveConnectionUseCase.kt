package com.alselwi.trackprices.domain.usecase

import com.alselwi.trackprices.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow

class ObserveConnectionUseCase(private val repository: StockRepository) {
    operator fun invoke(): Flow<Boolean> = repository.connectionStatus
}