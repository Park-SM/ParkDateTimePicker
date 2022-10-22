package com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.model

import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.UiModel

internal data class TimeUiModel(

    override val id: Int,

    var amPm: String? = null,

    var hour: Int,

    var minute: Int
): UiModel