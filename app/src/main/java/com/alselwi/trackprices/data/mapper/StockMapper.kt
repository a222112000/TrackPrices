package com.alselwi.trackprices.data.mapper

import com.alselwi.trackprices.data.model.StockDto
import com.alselwi.trackprices.domain.entity.Stock

object StockMapper {
    fun toDomain(
        dto: StockDto,
        oldPrice: Double?
    ): Stock {
        val change = if(oldPrice != null) dto.price else 0.00
        return Stock(
            symbol = dto.symbol,
            price = dto.price,
            change = change,
            isUp = change >= 0
        )
    }
}