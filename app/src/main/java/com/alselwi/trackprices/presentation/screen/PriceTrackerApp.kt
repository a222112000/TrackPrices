package com.alselwi.trackprices.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alselwi.trackprices.presentation.navigation.NavRoutes

@Composable
fun PriceTrackerApp(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = NavRoutes.Feed.route){
        composable(route = NavRoutes.Feed.route){
            FeedScreen(navController = navController)
        }
    }
}