package com.smparkworld.parkdatetimepicker.core.navigator

import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal class DateTimeModeNavigatorImpl : DateTimeModeNavigator {

    private lateinit var mode: DateTimeMode

    override fun init(mode: DateTimeMode?): Phase {
        this.mode = mode ?: DEFAULT_MODE
        return getNextPhase(Phase.INIT)
    }

    override fun getNextPhase(currentPhase: Phase?): Phase {
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
        }
    }

    private fun assertInitialized() {
        if (!::mode.isInitialized) {
            throw IllegalStateException(ERROR_NOT_INITIALIZED)
        }
    }

    companion object {
        private val DEFAULT_MODE = DateTimeMode.DATETIME

        private const val ERROR_NOT_INITIALIZED = "DateTimeMode is not initialized."
        private const val ERROR_INVALID_PHASE = "Invalid phase on DateTimeMode."
    }
}