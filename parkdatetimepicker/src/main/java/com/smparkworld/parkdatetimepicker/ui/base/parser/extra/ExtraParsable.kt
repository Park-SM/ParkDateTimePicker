package com.smparkworld.parkdatetimepicker.ui.base.parser.extra

import androidx.lifecycle.SavedStateHandle

interface ExtraParsable<T> {

    fun parse(handle: SavedStateHandle): T
}