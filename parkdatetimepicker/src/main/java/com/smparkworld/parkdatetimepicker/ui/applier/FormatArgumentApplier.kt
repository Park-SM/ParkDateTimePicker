package com.smparkworld.parkdatetimepicker.ui.applier

import com.smparkworld.parkdatetimepicker.model.listener.DateTitleFormatter
import com.smparkworld.parkdatetimepicker.model.listener.TimeTitleFormatter

internal object FormatArgumentApplier {

    private var dateTitleFormatter: DateTitleFormatter = DateTitleFormatter { year, month ->
        "${year}.${String.format("%02d", month)}"
    }

    private var timeTitleFormatter: TimeTitleFormatter = TimeTitleFormatter { amPm, hour, minute ->
        "$amPm $hour : ${String.format("%02d", minute)}"
    }

    @JvmStatic
    fun setDateTitleFormatter(formatter: DateTitleFormatter) {
        this.dateTitleFormatter = formatter
    }

    @JvmStatic
    fun setTimeTitleFormatter(formatter: TimeTitleFormatter) {
        this.timeTitleFormatter = formatter
    }

    @JvmStatic
    fun formatDateTitle(year: Int, month: Int): String {
        return dateTitleFormatter.onChangeTitle(year, month)
    }

    @JvmStatic
    fun formatTimeTitle(amPm: String, hour: Int, minute: Int): String {
        return timeTitleFormatter.onChangeTitle(amPm, hour, minute)
    }
}