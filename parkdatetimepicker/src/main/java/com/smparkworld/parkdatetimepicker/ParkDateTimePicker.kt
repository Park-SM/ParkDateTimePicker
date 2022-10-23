package com.smparkworld.parkdatetimepicker

import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.model.DateListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range.DateRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.DateTimeFragment
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.TimeListener

class ParkDateTimePicker private constructor() {

    companion object {

        @JvmStatic
        fun builder(activity: FragmentActivity): ParkDateTimePickerBuilder {
            return BuilderImpl(activity.supportFragmentManager)
        }

        @JvmStatic
        fun builder(fragment: Fragment): ParkDateTimePickerBuilder {
            return BuilderImpl(fragment.parentFragmentManager)
        }
    }

    private class BuilderImpl(
        private val fragmentManager: FragmentManager
    ) : ParkDateTimePickerBuilder {

        private var dateListener: DateListener? = null
        private var dateTimeListener: DateTimeListener? = null

        private var dateRangeListener: DateRangeListener? = null
        private var dateTimeRangeListener: DateTimeRangeListener? = null

        private var timeListener: TimeListener? = null

        private var title: String? = null
        private var titleResId: Int? = null

        private var dayOfWeekChars: Array<String>? = null

        private var primaryColorCode: String? = null
        private var primaryColorResId: Int? = null

        override fun setDateListener(listener: DateListener): ParkDateTimePickerBuilder {
            dateListener = listener
            return this
        }

        override fun setDateRangeListener(listener: DateRangeListener): ParkDateTimePickerBuilder {
            dateRangeListener = listener
            return this
        }

        override fun setTimeListener(listener: TimeListener): ParkDateTimePickerBuilder {
            timeListener = listener
            return this
        }

        override fun setDateTimeListener(listener: DateTimeListener): ParkDateTimePickerBuilder {
            dateTimeListener = listener
            return this
        }

        override fun setDateTimeRangeListener(listener: DateTimeRangeListener): ParkDateTimePickerBuilder {
            dateTimeRangeListener = listener
            return this
        }

        override fun setTitle(title: String): ParkDateTimePickerBuilder {
            this.title = title
            return this
        }

        override fun setTitle(@StringRes titleResId: Int): ParkDateTimePickerBuilder {
            this.titleResId = titleResId
            return this
        }

        override fun setDayOfWeekChars(chars: Array<String>): ParkDateTimePickerBuilder {
            this.dayOfWeekChars = chars
            return this
        }

        override fun setPrimaryColor(colorCode: String): ParkDateTimePickerBuilder {
            primaryColorCode = colorCode
            return this
        }

        override fun setPrimaryColor(@ColorInt colorResId: Int): ParkDateTimePickerBuilder {
            primaryColorResId = colorResId
            return this
        }

        override fun show() {
            val fragment = DateTimeFragment()
            val arguments = Bundle()

            title?.let {
                arguments.putString(ExtraKey.EXTRA_TITLE, it)
            }
            titleResId?.let {
                arguments.putInt(ExtraKey.EXTRA_TITLE_RES_ID, it)
            }
            dayOfWeekChars?.let {
                arguments.putStringArray(ExtraKey.EXTRA_DAY_OF_WEEK_CHARS, it)
            }
            primaryColorCode?.let {
                arguments.putString(ExtraKey.EXTRA_PRIMARY_COLOR_CODE, it)
            }
            primaryColorResId?.let {
                arguments.putInt(ExtraKey.EXTRA_PRIMARY_COLOR_RES_ID, it)
            }

            when {
                (dateListener != null) -> {
                    arguments.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.DATE)
                    fragment.setListener(dateListener!!)
                }
                (dateTimeListener != null) -> {
                    arguments.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.DATETIME)
                    fragment.setListener(dateTimeListener!!)
                }
                (dateRangeListener != null) -> {
                    arguments.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.DATE_RANGE)
                    fragment.setListener(dateRangeListener!!)
                }
                (dateTimeRangeListener != null) -> {
                    arguments.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.DATETIME_RANGE)
                    fragment.setListener(dateTimeRangeListener!!)
                }
                (timeListener != null) -> {
                    arguments.putSerializable(ExtraKey.EXTRA_MODE, DateTimeMode.TIME)
                    fragment.setListener(timeListener!!)
                }
            }
            fragment.arguments = arguments
            fragment.show(fragmentManager, DateTimeFragment::class.simpleName)
        }
    }
}