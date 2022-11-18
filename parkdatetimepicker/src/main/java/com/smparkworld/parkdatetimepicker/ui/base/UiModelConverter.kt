package com.smparkworld.parkdatetimepicker.ui.base

internal interface UiModelConverter<FROM, TO> {

    fun convert(from: FROM): TO
}