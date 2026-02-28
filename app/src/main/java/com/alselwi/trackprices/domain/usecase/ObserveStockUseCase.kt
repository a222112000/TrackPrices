package com.alselwi.trackprices.domain.usecase

import com.alselwi.trackprices.domain.entity.Stock
import com.alselwi.trackprices.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow

class ObserveStockUseCase(private val repository: StockRepository) {
    operator fun invoke(): Flow<List<Stock>> = repository.stocks
}
