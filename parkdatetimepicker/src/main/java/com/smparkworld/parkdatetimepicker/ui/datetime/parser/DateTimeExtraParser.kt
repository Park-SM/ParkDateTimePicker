package com.smparkworld.parkdatetimepicker.ui.datetime.parser

import androidx.lifecycle.SavedStateHandle
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.base.parser.extra.ExtraParsable
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeExtras
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeMode

internal class DateTimeExtraParser : ExtraParsable<DateTimeExtras> {

    override fun parse(handle: SavedStateHandle): DateTimeExtras {
        return DateTimeExtras(
            mode = handle.get<DateTimeMode>(ExtraKey.EXTRA_MODE) ?: DateTimeMode.DEFAULT_MODE,
            title = handle.get<String>(ExtraKey.EXTRA_TITLE),
            resetText = handle.get<String>(ExtraKey.EXTRA_RESET_TEXT),
            doneText = handle.get<String>(ExtraKey.EXTRA_DONE_TEXT),
        )
    }
}