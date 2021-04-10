package com.ninositsolution.paging.ui.base

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    private val _eventHandler = Channel<BaseEventHandler>()
    val baseEventHandler = _eventHandler.receiveAsFlow()

    var isListEmpty = ObservableField(false)

    sealed class BaseEventHandler {
        data class BaseErrorEvent(val message: String?) : BaseEventHandler()
        data class NotifyEvent(val message: String?) : BaseEventHandler()
        data class ValidationError(val message: String) : BaseEventHandler()
        object StartLoading : BaseEventHandler()
        object StopLoading : BaseEventHandler()
    }

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        triggerBaseEvent(BaseEventHandler.BaseErrorEvent(throwable.message))
        triggerBaseEvent(BaseEventHandler.StopLoading)
        Timber.e(throwable)
    }

    protected fun triggerBaseEvent(baseEvent: BaseEventHandler) =
        viewModelScope.launch { _eventHandler.send(baseEvent) }

    protected fun silentApiCall(function: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            function.invoke()
        }

    protected fun apiCall(function: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            triggerBaseEvent(BaseEventHandler.StartLoading)
            function.invoke()
            triggerBaseEvent(BaseEventHandler.StopLoading)
        }

    protected fun io(function: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
            function.invoke()
        }
}