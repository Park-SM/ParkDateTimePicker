package com.smparkworld.parkdatetimepicker.data

import com.smparkworld.parkdatetimepicker.model.DateData
import com.smparkworld.parkdatetimepicker.model.DayData
import com.smparkworld.parkdatetimepicker.model.MonthData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

internal interface DateRepository {

    suspend fun getDateData(minYearDiff: Int, maxYearDiff: Int): DateData
}

internal class DateRepositoryImpl(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : DateRepository {

    override suspend fun getDateData(minYearDiff: Int, maxYearDiff: Int): DateData {

        return withContext(defaultDispatcher) {

            val months = mutableListOf<MonthData>()
            val monthIdToDaysMap = hashMapOf<Int, List<DayData>>()

            val currentDate = SimpleDateFormat("yyyy-MM", Locale.KOREA).format(System.currentTimeMillis()).split("-")
            val currentYear = currentDate[0].toInt()
            val currentMonth = currentDate[1].toInt()

            var currentMonthPosition = 0
            var dayDataIndex = 0
            generateMonthData(currentYear, minYearDiff, maxYearDiff) { monthDataIndex, year, month ->

                if (currentYear == year && currentMonth == month) {
                    currentMonthPosition = monthDataIndex
                }

                val days = mutableListOf<DayData>()

                val calendar = GregorianCalendar(year, month - 1, 1)
                val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
                val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

                for (day in 0 until firstDayOfWeek) {
                    days.add(DayData.EMPTY_DAY)
                }

                for (day in 1..maxDay) {
                    val newDayData = DayData(
                        id = dayDataIndex++,
                        monthId = monthDataIndex,
                        day = day,
                        label = null
                    )
                    days.add(newDayData)
                }

                val newMonthData = MonthData(
                    id = monthDataIndex,
                    year = year,
                    month = month,
                    days = days
                )
                months.add(newMonthData)
                monthIdToDaysMap[monthDataIndex] = days
            }

            return@withContext DateData(
                months = months,
                monthIdToDaysMap = monthIdToDaysMap,
                currentMonthPosition = currentMonthPosition
            )
        }
    }

    private suspend fun generateMonthData(
        currentYear: Int,
        minYearDiff: Int,
        maxYearDiff: Int,
        perform: suspend (monthDataIndex: Int, year: Int, month: Int) -> Unit
    ) {
        val minYear = currentYear - minYearDiff
        val maxYear = currentYear + maxYearDiff
        var idx = 0
        for (year in minYear..maxYear) {
            for (month in 1..12) perform.invoke(idx++, year, month)
        }
    }
}