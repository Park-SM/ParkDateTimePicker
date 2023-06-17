package com.smparkworld.parkdatetimepicker.ui.datetime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigator
import com.smparkworld.parkdatetimepicker.core.DateTimeModeNavigatorImpl
import com.smparkworld.parkdatetimepicker.model.DateResult
import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.model.listener.BaseListener
import com.smparkworld.parkdatetimepicker.ui.applier.FormatArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.base.parser.extra.extras
import com.smparkworld.parkdatetimepicker.ui.datetime.mapper.DateTimeViewStateMapper
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeExtras
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeViewState

internal class DateTimeViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val extras: DateTimeExtras by extras(savedStateHandle)

    private val navigator: DateTimeModeNavigator = DateTimeModeNavigatorImpl()

    private val _phase = MutableLiveData<PhaseTransactionData>()
    val phase: LiveData<PhaseTransactionData> get() = _phase

    private val _viewState = MutableLiveData<DateTimeViewState>(DateTimeViewStateMapper.map(extras))
    val viewState: LiveData<DateTimeViewState> get() = _viewState

    private var selectedDate: DateResult? = null
    private var selectedTime: TimeResult? = null

    fun init(listener: BaseListener?) {
        _phase.value = navigator.init(
            mode = extras.mode ?: DateTimeMode.DEFAULT_MODE,
            doneListener = listener
        )
    }

    fun onResetClicked() {
        _phase.value = navigator.resetPhase()
        
        selectedDate = null
        selectedTime = null
        updateValidation()
        updateDateTimeResult()
    }

    fun onDoneClicked() {
        _phase.value = navigator.getNextPhase(
            selectedDate = selectedDate,
            selectedTime = selectedTime
        )
        updateValidation()
    }

    fun onSelectDate(selectedDate: DateResult) {
        this.selectedDate = selectedDate
        updateValidation()
        updateDateTimeResult()
    }

    fun onSelectTime(selectedTime: TimeResult) {
        this.selectedTime = selectedTime
        updateValidation()
        updateDateTimeResult()
    }

    private fun updateValidation() {
        val validation = navigator.isValidPhase(
            selectedDate = selectedDate,
            selectedTime = selectedTime
        )
        _viewState.value?.let { state ->
            _viewState.value = state.copy(
                validation = validation
            )
        }
    }

    private fun updateDateTimeResult() {
        _viewState.value?.let { state ->
            _viewState.value = state.copy(
                result = formatDateTimeResult()
            )
        }
    }

    private fun formatDateTimeResult(): String {
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
}