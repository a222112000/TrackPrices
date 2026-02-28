package com.alselwi.trackprices.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alselwi.trackprices.presentation.viewmodel.TrackPriceViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    symbol: String,
    viewModel: TrackPriceViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stock = state.stocks.firstOrNull{ it.symbol == symbol}
    if(stock == null){
        Text("Stock not found")
        return
    }
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(stock.symbol) })
            }
        ) { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(24.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = String.format("%.2f", stock.price),
                        style = MaterialTheme.typography.headlineLarge,
                        color = if (stock.isUp) Color.Green else Color.Red
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = if (stock.isUp) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                        contentDescription = if (stock.isUp) "Price Up" else "Price Down",
                        tint = if (stock.isUp) Color.Green else Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "About ${stock.symbol}: This is actively traded in global markets")
            }
        }
}