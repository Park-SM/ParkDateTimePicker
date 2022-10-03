package com.smparkworld.parkdatetimepicker.model

enum class DayOfWeek {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    companion object {

        @JvmStatic
        fun parse(index: Int): DayOfWeek? {
            return values().getOrNull(index)
        }
    }
}