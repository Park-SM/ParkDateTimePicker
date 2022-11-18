package com.smparkworld.parkdatetimepicker.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

internal open class BaseViewModel : ViewModel() {

    private var cachedScope: CoroutineScope? = null

    val viewModelScope: CoroutineScope
        get() {
            val scope = cachedScope
            if (scope != null) {
                return scope
            }
            return CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate).also {
                cachedScope = it
            }
        }

    override fun onCleared() {
        super.onCleared()
        cachedScope?.cancel()
    }
}