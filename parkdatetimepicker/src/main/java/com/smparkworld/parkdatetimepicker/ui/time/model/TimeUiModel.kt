package com.smparkworld.parkdatetimepicker.ui.time.model

import com.smparkworld.parkdatetimepicker.ui.base.UiModel

internal data class TimeUiModel(

    override val id: Int,

    val amPm: String,

    val hour: Int,

    val minute: Int

): UiModel