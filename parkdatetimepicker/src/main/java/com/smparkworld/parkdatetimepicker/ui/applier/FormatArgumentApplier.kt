package com.smparkworld.parkdatetimepicker.ui.applier

import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTitleFormatter

object FormatArgumentApplier {

    private var dateTitleFormatter: DateTitleFormatter = DateTitleFormatter { year, month ->
        "${year}.${String.format("%02d", month)}"
    }

    @JvmStatic
    fun setDateTitleFormatter(formatter: DateTitleFormatter) {
        this.dateTitleFormatter = formatter
    }

    @JvmStatic
    fun formatDateTitle(year: Int, month: Int): String {
        return dateTitleFormatter.onChangeTitle(year, month)
    }
}