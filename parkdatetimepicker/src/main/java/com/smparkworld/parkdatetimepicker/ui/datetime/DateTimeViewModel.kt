package com.smparkworld.parkdatetimepicker.ui.datetime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigator
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigatorImpl
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.model.DateResult
import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.model.listener.BaseListener
import com.smparkworld.parkdatetimepicker.ui.applier.FormatArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeMode

internal class DateTimeViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val navigator: DateTimeModeNavigator = DateTimeModeNavigatorImpl()

    private val _phase = MutableLiveData<PhaseTransactionData>()
    val phase: LiveData<PhaseTransactionData> get() = _phase

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    private var selectedDate: DateResult? = null
    private var selectedTime: TimeResult? = null

    fun init(listener: BaseListener?) {
        val mode = savedStateHandle.get<DateTimeMode>(ExtraKey.EXTRA_MODE) ?: DEFAULT_MODE

        _phase.value = navigator.init(mode, listener)
    }

    fun onResetClicked() {

    }

    fun onDoneClicked() {
        _phase.value = navigator.getNextPhase(
            selectedDate = selectedDate,
            selectedTime = selectedTime
        )
    }

    fun onSelectDate(selectedDate: DateResult) {
        this.selectedDate = selectedDate
        _result.value = getDateTimeResult()
    }

    fun onSelectTime(selectedTime: TimeResult) {
        this.selectedTime = selectedTime
        _result.value = getDateTimeResult()
    }

    private fun getDateTimeResult(): String {
        val stringBuilder = StringBuilder()

        selectedDate?.let { date ->
            val result = FormatArgumentApplier.formatDateResult(
                year = date.year,
                month = date.month,
                day = date.day
            )
            stringBuilder.append(result)
        }

        selectedTime?.let { time ->
            stringBuilder.append(" ")
            val result = FormatArgumentApplier.formatTimeResult(
                amPm = time.amPm,
                hour = time.hour,
                minute = time.minute
            )
            stringBuilder.append(result)
        }

        return stringBuilder.toString()
    }

    companion object {
        private val DEFAULT_MODE = DateTimeMode.DATETIME
    }
}