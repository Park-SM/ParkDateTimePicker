package com.smparkworld.parkdatetimepicker.ui.datetime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigator
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigatorImpl
import com.smparkworld.parkdatetimepicker.model.listener.BaseListener
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.model.DateResult
import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.ui.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeMode

internal class DateTimeViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navigator: DateTimeModeNavigator = DateTimeModeNavigatorImpl()

    private val _phase = MutableLiveData<PhaseTransactionData>()
    val phase: LiveData<PhaseTransactionData> get() = _phase

    fun init(listener: BaseListener?) {
        val mode = savedStateHandle.get<DateTimeMode>(ExtraKey.EXTRA_MODE) ?: DEFAULT_MODE

        _phase.value = navigator.init(mode, listener)
    }

    fun onSelectDate(selectedDate: DateResult) {
        _phase.value = navigator.getNextPhase(selectedDate = selectedDate)
    }

    fun onSelectTime(selectedTime: TimeResult) {
        _phase.value = navigator.getNextPhase(selectedTime = selectedTime)
    }

    companion object {
        private val DEFAULT_MODE = DateTimeMode.DATETIME
    }
}