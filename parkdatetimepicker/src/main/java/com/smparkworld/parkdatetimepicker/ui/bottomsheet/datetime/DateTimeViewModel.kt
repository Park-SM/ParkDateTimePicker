package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.smparkworld.parkdatetimepicker.core.navigator.DateTimeModeNavigator
import com.smparkworld.parkdatetimepicker.core.navigator.DateTimeModeNavigatorImpl
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal typealias PhaseTransactionData = Pair<Phase?, Phase>

internal class DateTimeViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val navigator: DateTimeModeNavigator = DateTimeModeNavigatorImpl()
) : ViewModel() {

    private var oldPhase: Phase? = null
    private var listener: BaseListener? = null

    private val _phase = MutableLiveData<PhaseTransactionData>()
    val phase: LiveData<PhaseTransactionData> get() = _phase

    fun init(listener: BaseListener?) {
        this.listener = listener

        val newPhase = navigator.init(
            mode = savedStateHandle.get<DateTimeMode>(ExtraKey.EXTRA_MODE)
        )
        _phase.value = getPhaseTransactionData(newPhase)
    }

    fun onSelectDate(selectedDate: SelectedDate) {
        Log.d("Test!!", "Selected date is $selectedDate")

        val newPhase = navigator.getNextPhase(oldPhase).also {
            if (it == Phase.DONE) onDone()
        }
        _phase.value = getPhaseTransactionData(newPhase)
    }

    private fun getPhaseTransactionData(newPhase: Phase): PhaseTransactionData {
        val tmpOldPhase = oldPhase
        oldPhase = newPhase
        return (tmpOldPhase to newPhase)
    }

    private fun onDone() {
        Log.d("Test!!", "Invoked onDone function.")
    }
}