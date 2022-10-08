package com.smparkworld.parkdatetimepicker.model

internal data class MonthData(
    val id: Int,
    val year: Int,
    val month: Int,
    val days: List<DayData>
)