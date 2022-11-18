package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.model.DateResult
import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.model.listener.BaseListener
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeMode

internal interface ListenerManager {

    fun init(mode: DateTimeMode, listener: BaseListener?)

    fun onDone(selectedDates: List<DateResult>, selectedTimes: List<TimeResult>)
}