package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.model.DateResult
import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.model.listener.BaseListener
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeMode

internal interface DateTimeModeNavigator {

    fun init(mode: DateTimeMode): PhaseTransactionData

    fun setDoneListener(listener: BaseListener)

    fun getNextPhase(
        selectedDate: DateResult? = null,
        selectedTime: TimeResult? = null
    ): PhaseTransactionData

    fun resetPhase(): PhaseTransactionData

    fun isValidPhase(
        selectedDate: DateResult? = null,
        selectedTime: TimeResult? = null
    ): Boolean
}