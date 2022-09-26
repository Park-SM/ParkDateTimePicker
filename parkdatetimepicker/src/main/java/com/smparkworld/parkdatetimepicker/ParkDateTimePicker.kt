package com.smparkworld.parkdatetimepicker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.model.SelectedDateTime
import com.smparkworld.parkdatetimepicker.model.SelectedTime

class ParkDateTimePicker private constructor() {


    companion object {

        @JvmStatic
        fun builder(activity: FragmentActivity): Builder {
            return BuilderImpl(activity.supportFragmentManager)
        }

        @JvmStatic
        fun builder(fragment: Fragment): Builder {
            return BuilderImpl(fragment.parentFragmentManager)
        }
    }

    interface Builder {

        fun setDateListener(date: SelectedDate): Builder

        fun setDateRangeListener(startDate: SelectedDate, endDate: SelectedDate): Builder

        fun setTimeListener(time: SelectedTime): Builder

        fun setTimeRangeListener(startTime: SelectedTime, endTime: SelectedTime): Builder

        fun setDateTimeListener(dateTime: SelectedDateTime): Builder

        fun setDateTimeRangeListener(startDate: SelectedDateTime, endDate: SelectedDateTime): Builder

        fun show()
    }

    private class BuilderImpl(
        private val fragmentManager: FragmentManager
    ) : Builder {

        override fun setDateListener(date: SelectedDate): Builder {
            TODO("Not yet implemented")
        }

        override fun setDateRangeListener(startDate: SelectedDate, endDate: SelectedDate): Builder {
            TODO("Not yet implemented")
        }

        override fun setTimeListener(time: SelectedTime): Builder {
            TODO("Not yet implemented")
        }

        override fun setTimeRangeListener(startTime: SelectedTime, endTime: SelectedTime): Builder {
            TODO("Not yet implemented")
        }

        override fun setDateTimeListener(dateTime: SelectedDateTime): Builder {
            TODO("Not yet implemented")
        }

        override fun setDateTimeRangeListener(
            startDate: SelectedDateTime,
            endDate: SelectedDateTime
        ): Builder {
            TODO("Not yet implemented")
        }

        override fun show() {
            TODO("Not yet implemented")
        }
    }
}