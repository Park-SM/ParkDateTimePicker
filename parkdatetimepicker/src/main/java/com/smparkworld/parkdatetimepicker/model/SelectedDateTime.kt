package com.smparkworld.parkdatetimepicker.model

data class SelectedDateTime(
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: DayOfWeek,
    val amPm: String?,
    val hour: Int,
    val minute: Int
)