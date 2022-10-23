package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigator
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigatorImpl
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.model.SelectedTime
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode

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

    fun onSelectDate(selectedDate: SelectedDate) {
        _phase.value = navigator.getNextPhase(selectedDate = selectedDate)
    }

    fun onSelectTime(selectedTime: SelectedTime) {
        _phase.value = navigator.getNextPhase(selectedTime = selectedTime)
    }

    companion object {
        private val DEFAULT_MODE = DateTimeMode.DATETIME
    }
}