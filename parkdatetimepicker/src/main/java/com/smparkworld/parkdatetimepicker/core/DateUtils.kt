package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.model.DayOfWeek
import java.util.*

internal object DateUtils {

    @JvmStatic
    fun getDayOfWeek(year: Int, month: Int, day: Int): DayOfWeek? {
        return DayOfWeek.parse(
            index = GregorianCalendar(year, month - 1, day).get(Calendar.DAY_OF_WEEK)
        )
    }
}