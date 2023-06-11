package com.smparkworld.parkdatetimepicker.core

internal object ExtraKey {

    const val EXTRA_MODE = "extra_mode"
    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_DAY_OF_WEEK_TEXTS = "extra_day_of_week_texts"
    const val EXTRA_RESET_TEXT = "extra_reset_text"
    const val EXTRA_DONE_TEXT = "extra_done_text"
    const val EXTRA_AM_PM_TEXTS = "extra_am_pm_texts"
    const val EXTRA_PRIMARY_COLOR_INT = "extra_primary_color_int"
    const val EXTRA_HIGHLIGHT_COLOR_INT = "extra_highlight_color_int"
    const val EXTRA_MIN_YEAR_DIFF = "extra_min_year_diff"
    const val EXTRA_MAX_YEAR_DIFF = "extra_max_year_diff"
}

internal object DefaultOption {

    const val MIN_YEAR_DIFF = 30
    const val MAX_YEAR_DIFF = 30

    const val TITLE = "Calendar"
    const val RESET = "Reset"
    const val DONE = "Done"

    const val AM = "AM"
    const val PM = "PM"

    val DAY_OF_WEEK = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    val AM_PM = listOf(AM, PM)
}