package com.smparkworld.parkdatetimepicker.model

import com.smparkworld.parkdatetimepicker.ui.date.model.MonthUiModel

internal data class MonthsInitModel(

    val months: List<MonthUiModel>,

    var initPosition: Int
)
