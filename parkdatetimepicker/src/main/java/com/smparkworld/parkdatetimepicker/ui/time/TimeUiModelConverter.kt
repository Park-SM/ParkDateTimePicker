package com.smparkworld.parkdatetimepicker.ui.time

import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.ui.base.UiModel
import com.smparkworld.parkdatetimepicker.ui.base.UiModelConverter
import com.smparkworld.parkdatetimepicker.ui.time.model.TimeUiModel

internal class TimeUiModelConverter {

    private val selectedTimeConverter = object: UiModelConverter<TimeResult, TimeUiModel> {
        override fun convert(from: TimeResult): TimeUiModel {
            return TimeUiModel(
                id = UiModel.NO_ID,
                amPm = from.amPm,
                hour = from.hour,
                minute = from.minute
            )
        }
    }

    private val timeUiModelConverter = object: UiModelConverter<TimeUiModel, TimeResult> {
        override fun convert(from: TimeUiModel): TimeResult {
            return TimeResult(
                amPm = from.amPm,
                hour = from.hour,
                minute = from.minute
            )
        }
    }

    fun convertToTimeUiModel(selectedTime: TimeResult): TimeUiModel {
        return selectedTimeConverter.convert(selectedTime)
    }

    fun convertToSelectedTime(uiModel: TimeUiModel): TimeResult {
        return timeUiModelConverter.convert(uiModel)
    }
}