package com.alselwi.trackprices.presentation.screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alselwi.trackprices.common.Constants.DELAY_500
import com.alselwi.trackprices.common.Constants.DURATION_300
import com.alselwi.trackprices.common.Constants.DURATION_600
import com.alselwi.trackprices.common.Constants.GREEN
import com.alselwi.trackprices.common.Constants.LIGHT_GREEN
import com.alselwi.trackprices.common.Constants.LIGHT_RED
import com.alselwi.trackprices.domain.entity.Stock
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun stockRow(
    stock: Stock,
    onClick:()-> Unit
){
    val flashColor = remember { Animatable(Color.Transparent) }
    LaunchedEffect(stock.price) {
        flashColor.animateTo(
            if(stock.isUp)
                Color(GREEN)
            else
                Color(LIGHT_RED),
            animationSpec = tween(DURATION_300)
        )
        delay(DELAY_500)
        flashColor.animateTo(Color.Transparent,
            animationSpec = tween(DURATION_600))
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(flashColor.value)
        .clickable{onClick()}
        .padding(horizontal = 16.dp,
            vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stock.symbol,
            style = MaterialTheme.typography.titleMedium
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = String.format(Locale.getDefault(),"%.2f", stock.price),
                style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = if (stock.isUp)
                Icons.Default.KeyboardArrowUp
            else
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = if(stock.isUp)
                    Color(LIGHT_GREEN)
                else
                    Color(LIGHT_RED)
                )
        }
    }
}
