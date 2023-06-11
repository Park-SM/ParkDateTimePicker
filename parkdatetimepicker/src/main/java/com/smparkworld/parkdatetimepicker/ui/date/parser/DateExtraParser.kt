package com.smparkworld.parkdatetimepicker.ui.date.parser

import androidx.lifecycle.SavedStateHandle
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.base.parser.extra.ExtraParsable
import com.smparkworld.parkdatetimepicker.ui.date.model.DateExtras

class DateExtraParser : ExtraParsable<DateExtras> {

    override fun parse(handle: SavedStateHandle): DateExtras {
        return DateExtras(
            minYearDiff = handle.get<Int>(ExtraKey.EXTRA_MIN_YEAR_DIFF),
            maxYearDiff = handle.get<Int>(ExtraKey.EXTRA_MAX_YEAR_DIFF),
            dayOfWeekTexts = handle.get<Array<String>>(ExtraKey.EXTRA_DAY_OF_WEEK_TEXTS).orEmpty().toList()
        )
    }
}