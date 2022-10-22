package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.model.SelectedTime
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode

internal interface ListenerManager {

    fun init(mode: DateTimeMode, listener: BaseListener?)

    fun onDone(selectedDates: List<SelectedDate>, selectedTimes: List<SelectedTime>)
}