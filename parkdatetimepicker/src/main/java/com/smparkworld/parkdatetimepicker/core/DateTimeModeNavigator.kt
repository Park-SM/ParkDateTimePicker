package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.model.SelectedTime
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode

internal interface DateTimeModeNavigator {

    fun init(mode: DateTimeMode, listener: BaseListener?): PhaseTransactionData

    fun getNextPhase(selectedDate: SelectedDate? = null, selectedTime: SelectedTime? = null): PhaseTransactionData
}