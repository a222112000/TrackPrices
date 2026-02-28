package com.alselwi.trackprices.data.mapper

import com.alselwi.trackprices.data.model.StockDto
import com.alselwi.trackprices.domain.entity.Stock

object StockMapper {
    fun toDomain(
        dto: StockDto,
        oldPrice: Double?
    ): Stock {
        val isUP = oldPrice?.let { dto.price > it } ?: true
        return Stock(
            symbol = dto.symbol,
            price = dto.price,
            isUp = isUP
        )
    }
}
