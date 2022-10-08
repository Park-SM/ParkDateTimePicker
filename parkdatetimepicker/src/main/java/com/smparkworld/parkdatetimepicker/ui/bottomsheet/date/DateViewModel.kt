package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.smparkworld.parkdatetimepicker.core.DateUtils
import com.smparkworld.parkdatetimepicker.data.DateRepository
import com.smparkworld.parkdatetimepicker.data.DateRepositoryImpl
import com.smparkworld.parkdatetimepicker.model.DateData
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.DayUiModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.MonthUiModel
import kotlinx.coroutines.launch

internal class DateViewModel(
    private val dateRepository: DateRepository = DateRepositoryImpl(),
    private val dateUiModelConverter: DateUiModelConverter = DateUiModelConverter()
) : BaseViewModel() {

    private val _dateData = MutableLiveData<DateData>()
    val months: LiveData<List<MonthUiModel>> get() = Transformations.map(_dateData, ::convertToMonthUiModels)

    private val _selectedDate = MutableLiveData<SelectedDate>()
    val selectedDate: LiveData<SelectedDate> get() = _selectedDate

    fun init(minYearDiff: Int, maxYearDiff: Int) {
        if (_dateData.value != null) return

        viewModelScope.launch {
            _dateData.value = dateRepository.getDateData(minYearDiff, maxYearDiff)
        }
    }

    fun onSelectDate(monthUiModel: MonthUiModel, dayUiModel: DayUiModel) {

        val day = dayUiModel.day.toIntOrNull() ?: return
        val dayOfWeek = DateUtils.getDayOfWeek(monthUiModel.year, monthUiModel.month, day) ?: return

        _selectedDate.value = SelectedDate(
            year = monthUiModel.year,
            month = monthUiModel.month,
            day = day,
            dayOfWeek = dayOfWeek
        )
    }

    private fun convertToMonthUiModels(dateData: DateData): List<MonthUiModel> {
        return dateUiModelConverter.convertToMonthUiModel(dateData)
    }
}