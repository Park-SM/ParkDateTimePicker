package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.model.PhaseTransactionData
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal interface DateTimeModeNavigator {

    fun init(mode: DateTimeMode, onDone: () -> Unit): PhaseTransactionData

    fun getNextPhase(): PhaseTransactionData
}