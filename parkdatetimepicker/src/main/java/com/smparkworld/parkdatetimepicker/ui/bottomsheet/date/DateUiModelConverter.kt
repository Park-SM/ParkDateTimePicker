package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date

import com.smparkworld.parkdatetimepicker.model.DateData
import com.smparkworld.parkdatetimepicker.model.DayData
import com.smparkworld.parkdatetimepicker.model.MonthData
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.UiModelConverter
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.DayUiModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.MonthUiModel

internal class DateUiModelConverter {

    private val monthUiConverter = object: UiModelConverter<MonthData, MonthUiModel> {
        override fun convert(from: MonthData): MonthUiModel {
            return MonthUiModel(
                id = from.id,
                year = from.year,
                month = from.month,
                dayUiModels = from.days.map {
                    dayUiModel.convert(it)
                }
            )
        }
    }

    private val dayUiModel = object: UiModelConverter<DayData, DayUiModel> {
        override fun convert(from: DayData): DayUiModel {
            return DayUiModel(
                id = from.id,
                monthId = from.monthId,
                day = from.day.toString(),
                isEmptyDay = DayData.isEmptyDay(from),
                isSelected = false,
                position = -1
            )
        }
    }

    fun convertToMonthUiModel(dateData: DateData): List<MonthUiModel> {
        return dateData.months.map { monthData ->
            monthUiConverter.convert(monthData)
        }
    }
}