package com.alselwi.trackprices.presentation.navigation

sealed class NavRoutes(val route: String) {
    data object Feed: NavRoutes("feed")
    data object Details: NavRoutes("details/{symbol}"){
        fun createRoute(symbol: String) = "details/$symbol"
        const val SYMBOL = "symbol"
    }
}