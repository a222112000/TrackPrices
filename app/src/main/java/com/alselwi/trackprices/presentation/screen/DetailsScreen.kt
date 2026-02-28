package com.alselwi.trackprices.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alselwi.trackprices.presentation.viewmodel.TrackPriceViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: TrackPriceViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    state.stocks.let { stock->
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(stock.get(0).symbol) })
            }
        ) { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp)) {
                Text("${stock.get(0).price} ${if(stock.get(0).isUp) Icons.Default.KeyboardArrowUp else Icons.Filled.ArrowDropDown}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = if(stock.get(0).isUp) Color.Green else Color.Red)
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "About ${stock.get(0).symbol}: This is actively traded in global markets")
            }
        }
    }
}