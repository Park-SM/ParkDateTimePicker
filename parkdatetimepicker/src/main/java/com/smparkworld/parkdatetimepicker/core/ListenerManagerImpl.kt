package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.model.SelectedDateTime
import com.smparkworld.parkdatetimepicker.model.SelectedTime
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.DateListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range.DateRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.TimeListener

internal class ListenerManagerImpl : ListenerManager {

    private lateinit var mode: DateTimeMode
    private var listener: BaseListener? = null

    override fun init(mode: DateTimeMode, listener: BaseListener?) {
        this.mode = mode
        this.listener = listener
    }

    override fun onDone(selectedDates: List<SelectedDate>, selectedTimes: List<SelectedTime>) {
        assertInitialized()
        when (mode) {
            DateTimeMode.DATE -> {
                (listener as? DateListener)?.let {
                    val result = selectedDates.getOrNull(0)
                    if (result != null) {
                        it.onSelectDate(result)
                    }
                }
            }
            DateTimeMode.DATETIME -> {
                (listener as? DateTimeListener)?.let {
                    val resultDate = selectedDates.getOrNull(0) ?: return
                    val resultTime = selectedTimes.getOrNull(0) ?: return
                    val result = SelectedDateTime(
                        year = resultDate.year,
                        month = resultDate.month,
                        day = resultDate.day,
                        dayOfWeek = resultDate.dayOfWeek,
                        amPm = resultTime.amPm,
                        hour = resultTime.hour,
                        minute = resultTime.minute
                    )
                    it.onSelectDateTime(result)
                }
            }
            DateTimeMode.DATE_RANGE -> {
                (listener as? DateRangeListener)?.let {
                    val resultStartDate = selectedDates.getOrNull(0) ?: return
                    val resultEndDate = selectedDates.getOrNull(1) ?: return

                    it.onSelectDateRange(resultStartDate, resultEndDate)
                }
            }
            DateTimeMode.DATETIME_RANGE -> {
                (listener as? DateTimeRangeListener)?.let {
                    val resultStartDate = selectedDates.getOrNull(0) ?: return
                    val resultStartTime = selectedTimes.getOrNull(0) ?: return
                    val resultStartDateTime = SelectedDateTime(
                        year = resultStartDate.year,
                        month = resultStartDate.month,
                        day = resultStartDate.day,
                        dayOfWeek = resultStartDate.dayOfWeek,
                        amPm = resultStartTime.amPm,
                        hour = resultStartTime.hour,
                        minute = resultStartTime.minute
                    )
                    val resultEndDate = selectedDates.getOrNull(1) ?: return
                    val resultEndTime = selectedTimes.getOrNull(1) ?: return
                    val resultEndDateTime = SelectedDateTime(
                        year = resultEndDate.year,
                        month = resultEndDate.month,
                        day = resultEndDate.day,
                        dayOfWeek = resultEndDate.dayOfWeek,
                        amPm = resultEndTime.amPm,
                        hour = resultEndTime.hour,
                        minute = resultEndTime.minute
                    )
                    it.onSelectDateTimeRange(resultStartDateTime, resultEndDateTime)
                }
            }
            DateTimeMode.TIME -> {
                (listener as? TimeListener)?.let {
                    val result = selectedTimes.getOrNull(0)
                    if (result != null) {
                        it.onSelectTime(result)
                    }
                }
            }
        }
    }

    private fun assertInitialized() {
        if (!::mode.isInitialized) {
            throw IllegalStateException(ERROR_NOT_INITIALIZED)
        }
    }

    companion object {

        private const val ERROR_NOT_INITIALIZED = "DateTimeMode is not initialized."
    }
}