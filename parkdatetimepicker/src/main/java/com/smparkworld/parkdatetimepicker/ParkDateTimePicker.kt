package com.smparkworld.parkdatetimepicker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.model.SelectedDate
import com.smparkworld.parkdatetimepicker.model.SelectedDateTime
import com.smparkworld.parkdatetimepicker.model.SelectedTime
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range.DateRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.DateTimeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.range.DateTimeRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.TimeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.range.TimeRangeListener

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

        fun setDateListener(listener: DateListener): Builder

        fun setDateRangeListener(listener: DateRangeListener): Builder

        fun setTimeListener(listener: TimeListener): Builder

        fun setTimeRangeListener(listener: TimeRangeListener): Builder

        fun setDateTimeListener(listener: DateTimeListener): Builder

        fun setDateTimeRangeListener(listener: DateTimeRangeListener): Builder

        fun show()
    }

    private class BuilderImpl(
        private val fragmentManager: FragmentManager
    ) : Builder {

        override fun setDateListener(listener: DateListener): Builder {
            TODO("Not yet implemented")
        }

        override fun setDateRangeListener(listener: DateRangeListener): Builder {
            TODO("Not yet implemented")
        }

        override fun setTimeListener(listener: TimeListener): Builder {
            TODO("Not yet implemented")
        }

        override fun setTimeRangeListener(listener: TimeRangeListener): Builder {
            TODO("Not yet implemented")
        }

        override fun setDateTimeListener(listener: DateTimeListener): Builder {
            TODO("Not yet implemented")
        }

        override fun setDateTimeRangeListener(listener: DateTimeRangeListener): Builder {
            TODO("Not yet implemented")
        }

        override fun show() {
            TODO("Not yet implemented")
        }
    }
}