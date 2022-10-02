package com.smparkworld.parkdatetimepicker

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range.DateRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.TimeListener

interface ParkDateTimePickerBuilder {

    fun setDateListener(listener: DateListener): ParkDateTimePickerBuilder

    fun setDateRangeListener(listener: DateRangeListener): ParkDateTimePickerBuilder

    fun setTimeListener(listener: TimeListener): ParkDateTimePickerBuilder

    fun setDateTimeListener(listener: DateTimeListener): ParkDateTimePickerBuilder

    fun setDateTimeRangeListener(listener: DateTimeRangeListener): ParkDateTimePickerBuilder

    fun setTitle(title: String): ParkDateTimePickerBuilder

    fun setTitle(@StringRes titleResId: Int): ParkDateTimePickerBuilder

    fun setTextColor(colorCode: String): ParkDateTimePickerBuilder

    fun setTextColor(@ColorRes colorResId: Int): ParkDateTimePickerBuilder

    fun show()
}