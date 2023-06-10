package com.smparkworld.parkdatetimepicker

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
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

    fun setTitle(@StringRes titleResId: Int): ParkDateTimePickerBuilder

    fun setDayOfWeekTexts(texts: Array<String>): ParkDateTimePickerBuilder

    fun setTimeDoneText(text: String): ParkDateTimePickerBuilder

    fun setAmPmTexts(texts: Array<String>): ParkDateTimePickerBuilder

    fun setPrimaryColor(colorCode: String): ParkDateTimePickerBuilder

    fun setPrimaryColor(@ColorRes colorResId: Int): ParkDateTimePickerBuilder

    fun setHighLightColor(colorCode: String): ParkDateTimePickerBuilder

    fun setHighLightColor(@ColorRes colorResId: Int): ParkDateTimePickerBuilder

    fun setMonthTitleFormatter(formatter: MonthTitleFormatter): ParkDateTimePickerBuilder

    fun setDateResultFormatter(formatter: DateResultFormatter): ParkDateTimePickerBuilder

    fun setTimeResultFormatter(formatter: TimeResultFormatter): ParkDateTimePickerBuilder

    fun show()
}