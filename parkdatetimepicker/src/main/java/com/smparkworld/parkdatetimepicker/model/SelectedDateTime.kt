package com.smparkworld.parkdatetimepicker.model

import java.time.DayOfWeek

data class SelectedDateTime(
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: DayOfWeek,
    val hour: Int,
    val minute: Int
)