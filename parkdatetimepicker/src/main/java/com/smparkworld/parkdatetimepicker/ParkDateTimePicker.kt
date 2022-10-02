package com.smparkworld.parkdatetimepicker

import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.model.ExtraKey
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range.DateRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.DateTimeFragment
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.listener.DateTimeRangeListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
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

        fun setTitle(title: String): Builder

        fun setTitle(@StringRes titleResId: Int): Builder

        fun setTextColor(colorCode: String): Builder

        fun setTextColor(@ColorRes colorResId: Int): Builder

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

        private var title: String? = null
        private var titleResId: Int? = null

        private var textColorCode: String? = null
        private var textColorResId: Int? = null

        override fun setDateListener(listener: DateListener): Builder {
            dateListener = listener
            return this
        }

        override fun setDateRangeListener(listener: DateRangeListener): Builder {
            dateRangeListener = listener
            return this
        }

        override fun setTimeListener(listener: TimeListener): Builder {
            timeListener = listener
            return this
        }

        override fun setDateTimeListener(listener: DateTimeListener): Builder {
            dateTimeListener = listener
            return this
        }

        override fun setDateTimeRangeListener(listener: DateTimeRangeListener): Builder {
            dateTimeRangeListener = listener
            return this
        }

        override fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        override fun setTitle(@StringRes titleResId: Int): Builder {
            this.titleResId = titleResId
            return this
        }

        override fun setTextColor(colorCode: String): Builder {
            textColorCode = colorCode
            return this
        }

        override fun setTextColor(@ColorInt colorResId: Int): Builder {
            textColorResId = colorResId
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
            textColorCode?.let {
                arguments.putString(ExtraKey.EXTRA_TEXT_COLOR_CODE, it)
            }
            textColorResId?.let {
                arguments.putInt(ExtraKey.EXTRA_TEXT_COLOR_RES_ID, it)
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