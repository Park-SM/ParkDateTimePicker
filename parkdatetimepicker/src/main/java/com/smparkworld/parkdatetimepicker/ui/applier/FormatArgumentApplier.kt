package com.smparkworld.parkdatetimepicker.ui.applier

import com.smparkworld.parkdatetimepicker.model.formatter.DateResultFormatter
import com.smparkworld.parkdatetimepicker.model.formatter.MonthTitleFormatter
import com.smparkworld.parkdatetimepicker.model.formatter.TimeResultFormatter

internal object FormatArgumentApplier {

    private var monthTitleFormatter: MonthTitleFormatter =
        MonthTitleFormatter { year, month ->
            "${year}.${String.format("%02d", month)}"
        }

    private var dateResultFormatter: DateResultFormatter =
        DateResultFormatter { _, month, day ->
            "${String.format("%02d", month)}.${String.format("%02d", day)}"
        }

    private var timeResultFormatter: TimeResultFormatter =
        TimeResultFormatter { amPm, hour, minute ->
            "$amPm ${String.format("%02d", hour)} : ${String.format("%02d", minute)}"
        }

    @JvmStatic
    fun setMonthTitleFormatter(formatter: MonthTitleFormatter) {
        this.monthTitleFormatter = formatter
    }

    @JvmStatic
    fun setDateResultFormatter(formatter: DateResultFormatter) {
        this.dateResultFormatter = formatter
    }

    @JvmStatic
    fun setTimeResultFormatter(formatter: TimeResultFormatter) {
        this.timeResultFormatter = formatter
    }

    @JvmStatic
    fun formatDateTitle(year: Int, month: Int): String {
        return monthTitleFormatter.onTitleChanged(year, month)
    }

    @JvmStatic
    fun formatDateResult(year: Int, month: Int, day: Int): String {
        return dateResultFormatter.onResultChanged(year, month, day)
    }

    @JvmStatic
    fun formatTimeResult(amPm: String, hour: Int, minute: Int): String {
        return timeResultFormatter.onResultChanged(amPm, hour, minute)
    }
}