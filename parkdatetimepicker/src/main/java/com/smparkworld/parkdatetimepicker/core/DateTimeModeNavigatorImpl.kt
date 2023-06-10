package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.extension.addIfNotNull
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

    override fun init(mode: DateTimeMode, listener: BaseListener?): PhaseTransactionData {
        this.mode = mode
        listenerManager.init(mode, listener)

        return getNextPhase()
    }

    override fun getNextPhase(
        selectedDate: DateResult?,
        selectedTime: TimeResult?
    ): PhaseTransactionData {
        assertInitialized()
        return when(mode) {
            DateTimeMode.DATETIME -> {
                when(currentPhase) {
                    Phase.INIT -> Phase.DATE
                    Phase.DATE -> {
                        selectedDates.addIfNotNull(selectedDate)
                        Phase.TIME
                    }
                    Phase.TIME -> {
                        selectedTimes.addIfNotNull(selectedTime)
                        Phase.DONE
                    }
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
            DateTimeMode.DATE -> {
                when (currentPhase) {
                    Phase.INIT -> Phase.DATE
                    Phase.DATE -> {
                        selectedDates.addIfNotNull(selectedDate)
                        Phase.DONE
                    }
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
            DateTimeMode.TIME -> {
                when (currentPhase) {
                    Phase.INIT -> Phase.TIME
                    Phase.TIME -> {
                        selectedTimes.addIfNotNull(selectedTime)
                        Phase.DONE
                    }
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
        }.let { newPhase ->
            if (newPhase == Phase.DONE) onDone()

            PhaseTransactionData(currentPhase, newPhase).also {
                currentPhase = newPhase
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