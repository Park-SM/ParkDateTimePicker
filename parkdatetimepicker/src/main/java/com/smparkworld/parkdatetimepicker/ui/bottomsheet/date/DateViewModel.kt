package com.smparkworld.parkdatetimepicker.ui.bottomsheet.date

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.smparkworld.parkdatetimepicker.data.DateRepository
import com.smparkworld.parkdatetimepicker.data.DateRepositoryImpl
import com.smparkworld.parkdatetimepicker.model.DateData
import com.smparkworld.parkdatetimepicker.model.DayData
import com.smparkworld.parkdatetimepicker.model.DayOfWeek
import com.smparkworld.parkdatetimepicker.model.MonthData
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.BaseViewModel
import kotlinx.coroutines.launch
import java.util.*

internal class DateViewModel(
    private val dateRepository: DateRepository = DateRepositoryImpl(),
) : BaseViewModel() {

    private val _dateData = MutableLiveData<DateData>()
    val months: LiveData<List<MonthData>> get() = Transformations.map(_dateData) { it.months }

    private val _selectedDate = MutableLiveData<SelectedDate>()
    val selectedDate: LiveData<SelectedDate> get() = _selectedDate

    fun init(minYearDiff: Int, maxYearDiff: Int) {
        if (_dateData.value != null) return

        viewModelScope.launch {
            _dateData.value = dateRepository.getDateData(minYearDiff, maxYearDiff)
        }
    }

    fun onSelectDate(monthData: MonthData, dayData: DayData) {

        val dayOfWeek = GregorianCalendar(
            monthData.year, monthData.month - 1, dayData.day
        ).get(Calendar.DAY_OF_WEEK).let {
            DayOfWeek.parse(it)
        } ?: return

        _selectedDate.value = SelectedDate(
            year = monthData.year,
            month = monthData.month,
            day = dayData.day,
            dayOfWeek = dayOfWeek
        )
    }
}