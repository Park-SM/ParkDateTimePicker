package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal class DateTimeModeNavigatorImpl : DateTimeModeNavigator {

    private lateinit var mode: DateTimeMode
    private lateinit var onDone: () -> Unit

    private var currentPhase: Phase = Phase.INIT

    override fun init(mode: DateTimeMode, onDone: () -> Unit): PhaseTransactionData {
        this.mode = mode
        this.onDone = onDone

        return getNextPhase()
    }

    override fun getNextPhase(): PhaseTransactionData {
        assertInitialized()
        return when(mode) {
            DateTimeMode.DATETIME -> {
                when(currentPhase) {
                    Phase.INIT -> Phase.DATE
                    Phase.DATE -> Phase.TIME
                    Phase.TIME -> Phase.DONE
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
            DateTimeMode.DATETIME_RANGE -> {
                when(currentPhase) {
                    Phase.INIT -> Phase.DATE_RANGE_FIRST
                    Phase.DATE_RANGE_FIRST -> Phase.TIME_RANGE_FIRST
                    Phase.TIME_RANGE_FIRST -> Phase.DATE_RANGE_SECOND
                    Phase.DATE_RANGE_SECOND -> Phase.TIME_RANGE_SECOND
                    Phase.TIME_RANGE_SECOND -> Phase.DONE
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
            DateTimeMode.DATE -> {
                when (currentPhase) {
                    Phase.INIT -> Phase.DATE
                    Phase.DATE -> Phase.DONE
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
            DateTimeMode.TIME -> {
                when (currentPhase) {
                    Phase.INIT -> Phase.TIME
                    Phase.TIME -> Phase.DONE
                    else -> throw IllegalStateException(ERROR_INVALID_PHASE)
                }
            }
            else -> throw IllegalStateException(ERROR_INVALID_PHASE)
        }.let { newPhase ->
            if (newPhase == Phase.DONE) onDone.invoke()

            PhaseTransactionData(currentPhase, newPhase).also {
                currentPhase = newPhase
            }
        }
    }

    private fun assertInitialized() {
        if (!::mode.isInitialized && !::onDone.isInitialized) {
            throw IllegalStateException(ERROR_NOT_INITIALIZED)
        }
    }

    companion object {

        private const val ERROR_NOT_INITIALIZED = "DateTimeMode is not initialized."
        private const val ERROR_INVALID_PHASE = "Invalid phase on DateTimeMode."
    }
}