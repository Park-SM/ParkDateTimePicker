package com.smparkworld.parkdatetimepicker.ui.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import com.smparkworld.parkdatetimepicker.core.DateUtils
import com.smparkworld.parkdatetimepicker.core.DefaultOption
import com.smparkworld.parkdatetimepicker.core.SingleLiveEvent
import com.smparkworld.parkdatetimepicker.data.DateRepository
import com.smparkworld.parkdatetimepicker.data.DateRepositoryImpl
import com.smparkworld.parkdatetimepicker.model.DateData
import com.smparkworld.parkdatetimepicker.model.DateResult
import com.smparkworld.parkdatetimepicker.model.MonthsInitModel
import com.smparkworld.parkdatetimepicker.ui.applier.FormatArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.base.parser.extra.extras
import com.smparkworld.parkdatetimepicker.ui.date.model.CalendarControlEvent
import com.smparkworld.parkdatetimepicker.ui.date.model.DateExtras
import com.smparkworld.parkdatetimepicker.ui.date.model.DayUiModel
import com.smparkworld.parkdatetimepicker.ui.date.model.MonthUiModel
import kotlinx.coroutines.launch

internal class DateViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val extras: DateExtras by extras(savedStateHandle)

    private val dateRepository: DateRepository = DateRepositoryImpl()
    private val dateUiModelConverter: DateUiModelConverter = DateUiModelConverter()

    private val _monthsInitModel = MutableLiveData<MonthsInitModel>()
    val monthsInitModel: LiveData<MonthsInitModel> get() = _monthsInitModel

    private val _selectedDate = SingleLiveEvent<DateResult>()
    val selectedDate: LiveData<DateResult> get() = _selectedDate

    val selectedDateTitle: LiveData<String> get() = Transformations.map(_monthPosition, ::convertDateTitle)

    private val _monthPosition = MutableLiveData<Int>()
    val monthPosition: LiveData<Int> get() = _monthPosition

    private val _weeks = MutableLiveData<List<String>>()
    val weeks: LiveData<List<String>> get() = _weeks

    private val _isScrollMode = MutableLiveData<Boolean>()
    val isScrollMode: LiveData<Boolean> get() =_isScrollMode

    init {
        initDateData()
        initWeekParams()
    }

    fun setScrollMode(isScrollMode: Boolean) {
        _isScrollMode.value = isScrollMode
    }

    fun onMonthScrolled(position: Int) {
        _monthPosition.value = position
        _monthsInitModel.value?.initPosition = position
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
                    maxPage = _monthsInitModel.value?.months?.size
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

    fun onResetClicked() {
        initDateData()
    }

    private fun initDateData() {
        val minYearDiff = extras.minYearDiff ?: DefaultOption.MIN_YEAR_DIFF
        val maxYearDiff = extras.maxYearDiff ?: DefaultOption.MAX_YEAR_DIFF

        viewModelScope.launch {
            _monthsInitModel.value = convertToMonthsInitModel(dateRepository.getDateData(minYearDiff, maxYearDiff))
        }
    }

    private fun initWeekParams() {
        _weeks.value = extras.dayOfWeekTexts.takeIf(List<String>::isNotEmpty)
            ?: DefaultOption.DAY_OF_WEEK
    }

    private fun convertToMonthsInitModel(dateData: DateData): MonthsInitModel {
        return MonthsInitModel(
            months = dateUiModelConverter.convertToMonthUiModel(dateData),
            initPosition = dateData.currentMonthPosition
        )
    }

    private fun getPrevPage(currentPage: Int?): Int? {
        return currentPage?.takeIf { it > 0 }?.minus(1)
    }

    private fun getNextPage(currentPage: Int?, maxPage: Int?): Int? {
        return currentPage?.takeIf { it < (maxPage ?: -1) }?.plus(1)
    }

    private fun convertDateTitle(monthPosition: Int): String {
        val monthData = _monthsInitModel.value?.months?.getOrNull(monthPosition)
        return FormatArgumentApplier.formatDateTitle(monthData?.year ?: 0, monthData?.month ?: 0)
    }
}