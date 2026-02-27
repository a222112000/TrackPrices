package com.alselwi.trackprices.domain.repository

import com.alselwi.trackprices.domain.entity.Stock
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    val stocks: Flow<List<Stock>>
    val connectionStatus: Flow<Boolean>

    fun start()
    fun stop()
}