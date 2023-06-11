package com.smparkworld.parkdatetimepicker

import androidx.annotation.ColorInt
import com.smparkworld.parkdatetimepicker.model.formatter.DateResultFormatter
import com.smparkworld.parkdatetimepicker.model.formatter.MonthTitleFormatter
import com.smparkworld.parkdatetimepicker.model.formatter.TimeResultFormatter
import com.smparkworld.parkdatetimepicker.model.listener.DateListener
import com.smparkworld.parkdatetimepicker.model.listener.DateTimeListener
import com.smparkworld.parkdatetimepicker.model.listener.TimeListener

interface ParkDateTimePickerBuilder {

    fun setDateListener(listener: DateListener): ParkDateTimePickerBuilder

    fun setTimeListener(listener: TimeListener): ParkDateTimePickerBuilder

    fun setDateTimeListener(listener: DateTimeListener): ParkDateTimePickerBuilder

    fun setTitle(title: String): ParkDateTimePickerBuilder

    fun setDayOfWeekTexts(texts: Array<String>): ParkDateTimePickerBuilder

    fun setResetText(text: String): ParkDateTimePickerBuilder

    fun setDoneText(text: String): ParkDateTimePickerBuilder

    fun setAmPmTexts(texts: Array<String>): ParkDateTimePickerBuilder

    fun setPrimaryColorInt(@ColorInt colorInt: Int): ParkDateTimePickerBuilder

    fun setHighLightColorInt(@ColorInt colorInt: Int): ParkDateTimePickerBuilder

    fun setMonthTitleFormatter(formatter: MonthTitleFormatter): ParkDateTimePickerBuilder

    fun setDateResultFormatter(formatter: DateResultFormatter): ParkDateTimePickerBuilder

    fun setTimeResultFormatter(formatter: TimeResultFormatter): ParkDateTimePickerBuilder

    fun show()
}