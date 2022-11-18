package com.smparkworld.parkdatetimepicker.ui.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.smparkworld.parkdatetimepicker.core.DateUtils
import com.smparkworld.parkdatetimepicker.data.DateRepository
import com.smparkworld.parkdatetimepicker.data.DateRepositoryImpl
import com.smparkworld.parkdatetimepicker.model.DateData
import com.smparkworld.parkdatetimepicker.core.DefaultOption
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.model.DateResult
import com.smparkworld.parkdatetimepicker.ui.applier.FormatArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.date.model.CalendarControlEvent
import com.smparkworld.parkdatetimepicker.ui.date.model.DayUiModel
import com.smparkworld.parkdatetimepicker.ui.date.model.MonthUiModel
import kotlinx.coroutines.launch

internal typealias MonthsData = Pair<List<MonthUiModel>, Int>

internal class DateViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val dateRepository: DateRepository = DateRepositoryImpl()
    private val dateUiModelConverter: DateUiModelConverter = DateUiModelConverter()

    private val _dateData = MutableLiveData<DateData>()
    val months: LiveData<MonthsData> get() = Transformations.map(_dateData, ::convertToMonthUiModels)

    private val _selectedDate = MutableLiveData<DateResult>()
    val selectedDate: LiveData<DateResult> get() = _selectedDate

    val selectedDateTitle: LiveData<String> get() = Transformations.map(_monthPosition, ::convertDateTitle)

    private val _monthPosition = MutableLiveData<Int>()
    val monthPosition: LiveData<Int> get() = _monthPosition

    init {
        initDateData()
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
                // To be added in version 1.1.0.
            }
        }
    }

    fun onSelectDate(monthUiModel: MonthUiModel, dayUiModel: DayUiModel) {
        val day = dayUiModel.day.toIntOrNull() ?: return
        val dayOfWeek = DateUtils.getDayOfWeek(monthUiModel.year, monthUiModel.month, day) ?: return

        _selectedDate.value = DateResult(
            year = monthUiModel.year,
            month = monthUiModel.month,
            day = day,
            dayOfWeek = dayOfWeek
        )
    }

    private fun initDateData() {
        val minYearDiff = savedStateHandle.get<Int>(ExtraKey.EXTRA_MIN_YEAR_DIFF) ?: DefaultOption.DEFAULT_MIN_YEAR_DIFF
        val maxYearDiff = savedStateHandle.get<Int>(ExtraKey.EXTRA_MAX_YEAR_DIFF) ?: DefaultOption.DEFAULT_MAX_YEAR_DIFF
        if (_dateData.value != null) return

        viewModelScope.launch {
            _dateData.value = dateRepository.getDateData(minYearDiff, maxYearDiff)
        }
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

    private fun convertDateTitle(monthPosition: Int): String {
        val monthData = _dateData.value?.months?.getOrNull(monthPosition)
        return FormatArgumentApplier.formatDateTitle(monthData?.year ?: 0, monthData?.month ?: 0)
    }
}