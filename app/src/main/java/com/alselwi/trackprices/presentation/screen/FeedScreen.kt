package com.alselwi.trackprices.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alselwi.trackprices.presentation.contract.FeedContract
import com.alselwi.trackprices.presentation.navigation.NavRoutes
import com.alselwi.trackprices.presentation.viewmodel.TrackPriceViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: TrackPriceViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when(effect){
                is FeedContract.Effect.NavigateToDetails -> {
                    navController.navigate(
                        NavRoutes.Details.createRoute(effect.symbol)
                    )
                }
                is FeedContract.Effect.ShowError -> {
                   // Snackbar()
                }
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text("Real-Time Prices") },
            navigationIcon = {
                val connectedIcon = "\uD83D\uDFE2"
                val disconnectedIcon = "\uD83D\uDD34"
                Text(if(state.isConnected) connectedIcon else disconnectedIcon)},
            actions = {
                TextButton(onClick = {
                    viewModel.setEvent(FeedContract.Event.ToggleFeed)
                }) {
                    Text(if(state.isRunning) "Stop" else "Start")
                }
            })
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(state.stocks){stock->
                StockRow(stock){
                    viewModel.setEvent(
                        FeedContract.Event.SymbolClicked(stock.symbol)
                    )
                }
            }
        }
    }
}