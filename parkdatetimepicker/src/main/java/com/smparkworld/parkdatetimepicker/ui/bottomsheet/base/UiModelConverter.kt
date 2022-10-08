package com.smparkworld.parkdatetimepicker.ui.bottomsheet.base

internal interface UiModelConverter<FROM, TO> {

    fun convert(from: FROM): TO
}