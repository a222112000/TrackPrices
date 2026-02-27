package com.alselwi.trackprices.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE: Any,EVENT: Any,EFFECT: Any>(
    initialState: STATE
): ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _event = MutableSharedFlow<EVENT>()
    private val _effect = Channel<EFFECT>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        observeEvent()
    }

    private fun observeEvent(){
        viewModelScope.launch {
            _event.collect {
                handleEvent(it)
            }
        }
    }

    fun setEvent(event: EVENT){
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun setState(reducer: STATE.()->STATE){
        val newState = state.value.reducer()
        _state.value = newState
    }

    protected fun setEffect(builder: ()-> EFFECT){
        viewModelScope.launch {
            _effect.send(builder())
        }
    }

    protected abstract fun handleEvent(event: EVENT)
}