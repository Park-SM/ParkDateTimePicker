package com.smparkworld.parkdatetimepicker.ui.time.model

import com.smparkworld.parkdatetimepicker.ui.base.UiModel

internal data class TimeUiModel(

    override val id: Int,

    var amPm: String,

    var hour: Int,

    var minute: Int
): UiModel