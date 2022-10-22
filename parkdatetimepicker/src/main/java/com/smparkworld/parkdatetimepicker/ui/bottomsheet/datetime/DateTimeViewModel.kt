package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigator
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigatorImpl
import com.smparkworld.parkdatetimepicker.core.ListenerManager
import com.smparkworld.parkdatetimepicker.core.ListenerManagerImpl
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.model.SelectedTime
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode

internal class DateTimeViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val listenerManager: ListenerManager = ListenerManagerImpl()
    private val navigator: DateTimeModeNavigator = DateTimeModeNavigatorImpl()

    private val _phase = MutableLiveData<PhaseTransactionData>()
    val phase: LiveData<PhaseTransactionData> get() = _phase

    private val selectedDates = mutableListOf<SelectedDate>()
    private val selectedTimes = mutableListOf<SelectedTime>()

    fun init(listener: BaseListener?) {
        val mode = savedStateHandle.get<DateTimeMode>(ExtraKey.EXTRA_MODE) ?: DEFAULT_MODE

        listenerManager.init(mode, listener)
        navigator.init(mode, ::onDone).let {
            _phase.value = it
        }
    }

    fun onSelectDate(selectedDate: SelectedDate) {
        selectedDates.add(selectedDate)
        gotoNextPhase()
    }

    fun onSelectTime(selectedTime: SelectedTime) {
        selectedTimes.add(selectedTime)
        gotoNextPhase()
    }

    private fun onDone() {
        listenerManager.onDone(selectedDates, selectedTimes)
    }

    private fun gotoNextPhase() {
        _phase.value = navigator.getNextPhase()
    }

    companion object {
        private val DEFAULT_MODE = DateTimeMode.DATETIME
    }
}