package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.extension.addIfNonNull
import com.smparkworld.parkdatetimepicker.model.DateResult
import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.model.listener.BaseListener
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.datetime.model.Phase

internal class DateTimeModeNavigatorImpl(
    private val listenerManager: ListenerManager = ListenerManagerImpl()
): DateTimeModeNavigator {

    private lateinit var mode: DateTimeMode

    private var currentPhase: Phase = Phase.INIT

    private val selectedDates = mutableListOf<DateResult>()
    private val selectedTimes = mutableListOf<TimeResult>()

    override fun init(mode: DateTimeMode): PhaseTransactionData {
        this.mode = mode
        return getNextPhase()
    }

    override fun setDoneListener(listener: BaseListener) {
        assertInitialized()

        listenerManager.init(mode, listener)
    }

    override fun getNextPhase(
        selectedDate: DateResult?,
        selectedTime: TimeResult?
    ): PhaseTransactionData {
        assertInitialized()

        val newPhase = when(mode) {
            DateTimeMode.DATETIME -> {
                when(currentPhase) {
                    Phase.INIT -> Phase.DATE
                    Phase.DATE -> {
                        val isAdded = selectedDates.addIfNonNull(selectedDate)
                        if (isAdded) Phase.TIME else Phase.DATE
                    }
                    Phase.TIME -> {
                        val isAdded = selectedTimes.addIfNonNull(selectedTime)
                        if (isAdded) Phase.DONE else Phase.TIME
                    }
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
            DateTimeMode.DATE -> {
                when (currentPhase) {
                    Phase.INIT -> Phase.DATE
                    Phase.DATE -> {
                        val isAdded = selectedDates.addIfNonNull(selectedDate)
                        if (isAdded) Phase.DONE else Phase.DATE
                    }
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
            DateTimeMode.TIME -> {
                when (currentPhase) {
                    Phase.INIT -> Phase.TIME
                    Phase.TIME -> {
                        val isAdded = selectedTimes.addIfNonNull(selectedTime)
                        if (isAdded) Phase.DONE else Phase.TIME
                    }
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
        }

        if (newPhase == Phase.DONE) onDone()

        return PhaseTransactionData(currentPhase, newPhase).also {
            currentPhase = newPhase
        }
    }

    override fun resetPhase(): PhaseTransactionData {
        selectedDates.clear()
        selectedTimes.clear()
        currentPhase = Phase.INIT
        return getNextPhase()
    }

    override fun isValidPhase(
        selectedDate: DateResult?,
        selectedTime: TimeResult?
    ): Boolean {
        return when(mode) {
            DateTimeMode.DATETIME -> {
                when(currentPhase) {
                    Phase.INIT -> true
                    Phase.DATE -> (selectedDate != null)
                    Phase.TIME -> (selectedTime != null)
                    else -> false
                }
            }
            DateTimeMode.DATE -> {
                when (currentPhase) {
                    Phase.INIT -> true
                    Phase.DATE -> (selectedDate != null)
                    else -> false
                }
            }
            DateTimeMode.TIME -> {
                when (currentPhase) {
                    Phase.INIT -> true
                    Phase.TIME -> (selectedTime != null)
                    else -> false
                }
            }
        }
    }

    private fun assertInitialized() {
        if (!::mode.isInitialized) {
            throw IllegalStateException(ERROR_NOT_INITIALIZED)
        }
    }

    private fun onDone() {
        listenerManager.onDone(selectedDates, selectedTimes)
    }

    companion object {

        private const val ERROR_NOT_INITIALIZED = "DateTimeMode is not initialized."
        private const val ERROR_INVALID_PHASE = "Invalid phase on DateTimeMode."
    }
}