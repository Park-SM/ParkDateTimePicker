package com.smparkworld.parkdatetimepicker.model

internal data class DateData(
    val months: List<MonthData>,
    val monthIdToDaysMap: Map<Int, List<DayData>>,
    val currentMonthPosition: Int
)