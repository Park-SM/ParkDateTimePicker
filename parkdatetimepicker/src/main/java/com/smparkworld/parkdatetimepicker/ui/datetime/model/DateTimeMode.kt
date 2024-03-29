package com.smparkworld.parkdatetimepicker.ui.datetime.model

internal enum class DateTimeMode {
    DATE,
    DATETIME,
    TIME;

    companion object {

        val DEFAULT_MODE = DATETIME
    }
}