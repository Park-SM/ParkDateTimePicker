package com.smparkworld.parkdatetimepicker.model

internal data class DayData(
    val id: Int,
    val monthId: Int,
    val day: Int,
    val label: String?
) {

    companion object {

        val EMPTY_DAY = DayData(-9, -9, -9, null)

        @JvmStatic
        fun isEmptyDay(dayData: DayData): Boolean = dayData == EMPTY_DAY
    }
}
