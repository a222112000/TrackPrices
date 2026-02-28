package com.alselwi.trackprices.data.repository

import com.alselwi.trackprices.common.Constants.DELAY_2000
import com.alselwi.trackprices.common.Constants.VALUE_100
import com.alselwi.trackprices.common.Constants.VALUE_800
import com.alselwi.trackprices.data.mapper.StockMapper
import com.alselwi.trackprices.data.model.StockDto
import com.alselwi.trackprices.data.remote.WebSocketManager
import com.alselwi.trackprices.domain.entity.Stock
import com.alselwi.trackprices.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random

class StockRepositoryImpl(
    private val webSocketManager: WebSocketManager
) : StockRepository {
    private val symbols = listOf(
        "AAPL","GOOG","TSLA","AMZN","MSFT","NVDA","META","NFLX","AMD",
        "INTC","ORCL","IBM","CRM","UBER","SHOP","BABA","PYPL","SQ","DIS","SONY",
        "ADBE","CSCO","QCOM","TXN","AVGO"
    )
    private val _stocks = MutableStateFlow<List<Stock>>(emptyList())
    override val stocks: Flow<List<Stock>>
        get() = _stocks
    override val connectionStatus: Flow<Boolean>
        get() = webSocketManager.connected

    private var job: Job? = null
    private val priceMap = mutableMapOf<String, Double>()
    override fun start() {
        Timber.d("xxxxThis is called")
        webSocketManager.connect()
        job = CoroutineScope(Dispatchers.IO).launch {
            while (isActive){
                symbols.forEach { symbol->
                    val newPrice = Random.nextDouble(VALUE_100, VALUE_800)
                    val message = "$symbol:$newPrice"
                    webSocketManager.send(message)
                }
                delay(DELAY_2000)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            webSocketManager.incoming.collect { message->
                Timber.d("xxxxThis is called $message")
                val parts = message.split(":")
                if(parts.size == 2){
                    val symbol = parts[0]
                    val price = parts[1].toDouble()

                    val oldPrice = priceMap[symbol]
                    priceMap[symbol] = price

                    val dto = StockDto(symbol,price)
                    val stock = StockMapper.toDomain(dto, oldPrice)
                    _stocks.update { current->
                        (current.filterNot { it.symbol == symbol } + stock)
                            .sortedByDescending { it.price }
                    }
                }
            }
        }
    }

    override fun stop() {
        job?.cancel()
        webSocketManager.disconnect()
    }

}
