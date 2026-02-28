package com.alselwi.trackprices.domain.entity

data class Stock(
    val symbol: String,
    val price: Double,
    val isUp: Boolean
)
