package com.alselwi.trackprices.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alselwi.trackprices.presentation.navigation.NavRoutes

@Composable
fun PriceTrackerApp(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = NavRoutes.Feed.route){
        composable(route = NavRoutes.Feed.route){
            FeedScreen(navController = navController)
        }
        composable(route = NavRoutes.Details.route,
            arguments = listOf(navArgument(NavRoutes.Details.SYMBOL){
                type = NavType.StringType
            })){ backStackEntry ->
            val symbol = backStackEntry.arguments?.getString(NavRoutes.Details.SYMBOL) ?: ""
            DetailsScreen(symbol = symbol)
        }
    }
}