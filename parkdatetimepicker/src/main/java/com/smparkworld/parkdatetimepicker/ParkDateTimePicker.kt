package com.smparkworld.parkdatetimepicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range.DateRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.DateTimeFragment
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.TimeListener

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

        fun setDateTimeListener(listener: DateTimeListener): Builder

        fun setDateTimeRangeListener(listener: DateTimeRangeListener): Builder

        fun show()
    }

    private class BuilderImpl(
        private val fragmentManager: FragmentManager
    ) : Builder {

        private var dateListener: DateListener? = null
        private var dateTimeListener: DateTimeListener? = null

        private var dateRangeListener: DateRangeListener? = null
        private var dateTimeRangeListener: DateTimeRangeListener? = null

        private var timeListener: TimeListener? = null

        override fun setDateListener(listener: DateListener): Builder {
            TODO("Not yet implemented")
        }

        override fun setDateRangeListener(listener: DateRangeListener): Builder {
            TODO("Not yet implemented")
        }

        override fun setTimeListener(listener: TimeListener): Builder {
            TODO("Not yet implemented")
        }

        override fun setDateTimeListener(listener: DateTimeListener): Builder {
            dateTimeListener = listener
            return this
        }

        override fun setDateTimeRangeListener(listener: DateTimeRangeListener): Builder {
            TODO("Not yet implemented")
        }

        override fun show() {
            val fragment = DateTimeFragment()
            val bundle = Bundle()

            when {
                (dateListener != null) -> {
                    bundle.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.DATE)
                    fragment.setListeners(dateListener!!)
                }
                (dateTimeListener != null) -> {
                    bundle.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.DATETIME)
                    fragment.setListeners(dateTimeListener!!)
                }
                (dateRangeListener != null) -> {
                    bundle.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.DATE_RANGE)
                    fragment.setListeners(dateRangeListener!!)
                }
                (dateTimeRangeListener != null) -> {
                    bundle.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.DATETIME_RANGE)
                    fragment.setListeners(dateTimeRangeListener!!)
                }
                (timeListener != null) -> {
                    bundle.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.TIME)
                    fragment.setListeners(timeListener!!)
                }
            }

            fragment.show(fragmentManager, DateTimeFragment::class.simpleName)
        }
    }
}