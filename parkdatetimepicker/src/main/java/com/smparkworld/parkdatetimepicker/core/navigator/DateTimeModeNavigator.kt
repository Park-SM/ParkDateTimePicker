package com.smparkworld.parkdatetimepicker.core.navigator

import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal interface DateTimeModeNavigator {

    fun init(mode: DateTimeMode?): Phase

    fun getNextPhase(currentPhase: Phase?): Phase
}