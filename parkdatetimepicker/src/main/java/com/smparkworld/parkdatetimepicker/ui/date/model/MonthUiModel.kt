package com.smparkworld.parkdatetimepicker.ui.date.model

import com.smparkworld.parkdatetimepicker.ui.base.UiModel

internal data class MonthUiModel(

    override val id: Int,

    val year: Int,

    val month: Int,

    var dayUiModels: List<DayUiModel>,

) : UiModel