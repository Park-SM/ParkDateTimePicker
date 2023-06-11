package com.smparkworld.parkdatetimepicker.ui.time.parser

import androidx.lifecycle.SavedStateHandle
import com.smparkworld.parkdatetimepicker.core.DefaultOption
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.base.parser.extra.ExtraParsable
import com.smparkworld.parkdatetimepicker.ui.time.model.TimeExtras

class TimeExtraParser : ExtraParsable<TimeExtras> {

    override fun parse(handle: SavedStateHandle): TimeExtras {
        val amPmTexts = handle.get<Array<String>>(ExtraKey.EXTRA_AM_PM_TEXTS).orEmpty()
        return if (amPmTexts.size == 2) {
            TimeExtras(
                am = amPmTexts[0],
                pm = amPmTexts[1],
                amPmTexts = amPmTexts.toList()
            )
        } else {
            TimeExtras(
                am = null,
                pm = null,
                amPmTexts = emptyList()
            )
        }
    }
}