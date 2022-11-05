package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.smparkworld.parkdatetimepicker.core.DateUtils
import com.smparkworld.parkdatetimepicker.data.DateRepository
import com.smparkworld.parkdatetimepicker.data.DateRepositoryImpl
import com.smparkworld.parkdatetimepicker.model.DateData
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.ui.applier.FormatArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.CalendarControlEvent
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.DayUiModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.MonthUiModel
import kotlinx.coroutines.launch

internal typealias MonthsData = Pair<List<MonthUiModel>, Int>

internal class DateViewModel(
    private val dateRepository: DateRepository = DateRepositoryImpl(),
    private val dateUiModelConverter: DateUiModelConverter = DateUiModelConverter()
) : BaseViewModel() {

    private val _dateData = MutableLiveData<DateData>()
    val months: LiveData<MonthsData> get() = Transformations.map(_dateData, ::convertToMonthUiModels)

    private val _selectedDate = MutableLiveData<SelectedDate>()
    val selectedDate: LiveData<SelectedDate> get() = _selectedDate

    val selectedDateTitle: LiveData<String> get() = MediatorLiveData<String>().apply {
        addSource(_dateData) {
            value = getDateTitle(it, null)
        }
        addSource(_monthPosition) {
            value = getDateTitle(null, it)
        }
    }

    private val _monthPosition = MutableLiveData<Int>()
    val monthPosition: LiveData<Int> get() = _monthPosition

    fun init(minYearDiff: Int, maxYearDiff: Int) {
        if (_dateData.value != null) return

        viewModelScope.launch {
            _dateData.value = dateRepository.getDateData(minYearDiff, maxYearDiff)
        }
    }

    fun onScrollMonth(position: Int) {
        _monthPosition.value = position
    }

    fun onClickCalendarControl(event: CalendarControlEvent) {
        when (event) {
            is CalendarControlEvent.PrevPage -> {
                val position = getPrevPage(currentPage = _monthPosition.value)
                if (position != null) {
                    _monthPosition.value = position
                }
            }
            is CalendarControlEvent.NextPage -> {
                val position = getNextPage(
                    currentPage = _monthPosition.value,
                    maxPage = _dateData.value?.months?.size
                )
                if (position != null) {
                    _monthPosition.value = position
                }
            }
            is CalendarControlEvent.JumpPage -> {
                val position = getJumpPage(
                    targetYear = event.year,
                    targetMonth = event.month
                )
                if (position != null) {
                    _monthPosition.value = position
                }
            }
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

    private fun convertToMonthUiModels(dateData: DateData): MonthsData {
        return (dateUiModelConverter.convertToMonthUiModel(dateData) to dateData.currentMonthPosition)
    }

    private fun getPrevPage(currentPage: Int?): Int? {
        return currentPage?.takeIf { it > 0 }?.minus(1)
    }

    private fun getNextPage(currentPage: Int?, maxPage: Int?): Int? {
        return currentPage?.takeIf { it < (maxPage ?: -1) }?.plus(1)
    }

    private fun getJumpPage(targetYear: Int, targetMonth: Int): Int? {
        var position: Int? = null

        _dateData.value?.months?.forEachIndexed { index, monthData ->
            if (monthData.year == targetYear && monthData.month == targetMonth) {
                position = index
            }
        }
        return position
    }

    private fun getDateTitle(dateData: DateData?, monthPosition: Int?): String? {
        val monthData = when {
            (dateData != null) -> {
                dateData.months.getOrNull(dateData.currentMonthPosition)
            }
            (monthPosition != null) -> {
                _dateData.value?.months?.getOrNull(monthPosition)
            }
            else -> return null
        }
        return FormatArgumentApplier.formatDateTitle(monthData?.year ?: 0, monthData?.month ?: 0)
    }
}