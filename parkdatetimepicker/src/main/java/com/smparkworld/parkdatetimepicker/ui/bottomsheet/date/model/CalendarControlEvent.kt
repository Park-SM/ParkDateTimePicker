package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model

sealed class CalendarControlEvent {

    object PrevPage : CalendarControlEvent()

    object NextPage: CalendarControlEvent()

    data class JumpPage(
        val year: Int,
        val month: Int
    ) : CalendarControlEvent()
}