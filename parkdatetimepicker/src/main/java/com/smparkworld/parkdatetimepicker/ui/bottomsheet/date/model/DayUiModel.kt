package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model

import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.UiModel

internal data class DayUiModel(

    override val id: Int,

    val monthId: Int,

    val day: String,

    val isEmptyDay: Boolean,

    var isSelected: Boolean = false,

    var position: Int = -1
) : UiModel