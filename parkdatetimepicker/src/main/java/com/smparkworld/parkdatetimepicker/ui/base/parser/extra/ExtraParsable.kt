package com.smparkworld.parkdatetimepicker.ui.base.parser.extra

import androidx.lifecycle.SavedStateHandle

internal interface ExtraParsable<T> {

    fun parse(handle: SavedStateHandle): T
}