package com.smparkworld.parkdatetimepicker.model

import java.time.DayOfWeek

data class SelectedDate(
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: DayOfWeek
)
