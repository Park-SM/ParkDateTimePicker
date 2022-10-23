package com.smparkworld.parkdatetimepicker.ui.bottomsheet.time

import com.smparkworld.parkdatetimepicker.model.SelectedTime
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.UiModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.UiModelConverter
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.model.TimeUiModel

internal class TimeUiModelConverter {

    private val selectedTimeConverter = object: UiModelConverter<SelectedTime, TimeUiModel> {
        override fun convert(from: SelectedTime): TimeUiModel {
            return TimeUiModel(
                id = UiModel.NO_ID,
                amPm = from.amPm,
                hour = from.hour,
                minute = from.minute
            )
        }
    }

    private val timeUiModelConverter = object: UiModelConverter<TimeUiModel, SelectedTime> {
        override fun convert(from: TimeUiModel): SelectedTime {
            return SelectedTime(
                amPm = from.amPm,
                hour = from.hour,
                minute = from.minute
            )
        }
    }

    fun convertToTimeUiModel(selectedTime: SelectedTime): TimeUiModel {
        return selectedTimeConverter.convert(selectedTime)
    }

    fun convertToSelectedTime(uiModel: TimeUiModel): SelectedTime {
        return timeUiModelConverter.convert(uiModel)
    }
}