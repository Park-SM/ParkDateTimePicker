package com.smparkworld.parkdatetimepicker.ui.datetime.mapper

import com.smparkworld.parkdatetimepicker.core.DefaultOption
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeExtras
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeViewState

internal object DateTimeViewStateMapper {

    fun map(
        extras: DateTimeExtras,
    ): DateTimeViewState {
        return DateTimeViewState(
            title = extras.title ?: DefaultOption.TITLE,
            result = "",
            resetText = extras.resetText ?: DefaultOption.RESET,
            doneText = extras.doneText ?: DefaultOption.DONE,
            validation = false
        )
    }
}