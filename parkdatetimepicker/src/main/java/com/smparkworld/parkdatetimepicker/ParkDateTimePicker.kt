package com.smparkworld.parkdatetimepicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.model.formatter.DateResultFormatter
import com.smparkworld.parkdatetimepicker.model.formatter.MonthTitleFormatter
import com.smparkworld.parkdatetimepicker.model.formatter.TimeResultFormatter
import com.smparkworld.parkdatetimepicker.model.listener.DateListener
import com.smparkworld.parkdatetimepicker.model.listener.DateTimeListener
import com.smparkworld.parkdatetimepicker.model.listener.TimeListener
import com.smparkworld.parkdatetimepicker.ui.applier.FormatArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.datetime.DateTimeFragment
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeMode

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

        private var timeListener: TimeListener? = null

        private var title: String? = null

        private var dayOfWeekTexts: Array<String>? = null

        private var resetText: String? = null

        private var doneText: String? = null

        private var amPmTexts: Array<String>? = null

        private var primaryColorInt: Int? = null
        private var highLightColorInt: Int? = null

        private var monthTitleFormatter: MonthTitleFormatter? = null
        private var dateResultFormatter: DateResultFormatter? = null
        private var timeResultFormatter: TimeResultFormatter? = null

        override fun setDateListener(listener: DateListener): ParkDateTimePickerBuilder {
            this.dateListener = listener
            return this
        }

        override fun setTimeListener(listener: TimeListener): ParkDateTimePickerBuilder {
            this.timeListener = listener
            return this
        }

        override fun setDateTimeListener(listener: DateTimeListener): ParkDateTimePickerBuilder {
            this.dateTimeListener = listener
            return this
        }

        override fun setTitle(title: String): ParkDateTimePickerBuilder {
            this.title = title
            return this
        }

        override fun setDayOfWeekTexts(texts: Array<String>): ParkDateTimePickerBuilder {
            this.dayOfWeekTexts = texts
            return this
        }

        override fun setResetText(text: String): ParkDateTimePickerBuilder {
            this.resetText = text
            return this
        }

        override fun setDoneText(text: String): ParkDateTimePickerBuilder {
            this.doneText = text
            return this
        }

        override fun setAmPmTexts(texts: Array<String>): ParkDateTimePickerBuilder {
            this.amPmTexts = texts
            return this
        }

        override fun setPrimaryColorInt(colorInt: Int): ParkDateTimePickerBuilder {
            this.primaryColorInt = colorInt
            return this
        }

        override fun setHighLightColorInt(colorInt: Int): ParkDateTimePickerBuilder {
            this.highLightColorInt = colorInt
            return this
        }

        override fun setMonthTitleFormatter(formatter: MonthTitleFormatter): ParkDateTimePickerBuilder {
            this.monthTitleFormatter = formatter
            return this
        }

        override fun setDateResultFormatter(formatter: DateResultFormatter): ParkDateTimePickerBuilder {
            this.dateResultFormatter = formatter
            return this
        }

        override fun setTimeResultFormatter(formatter: TimeResultFormatter): ParkDateTimePickerBuilder {
            this.timeResultFormatter = formatter
            return this
        }

        override fun show() {
            val fragment = DateTimeFragment()
            val arguments = Bundle()

            title?.let {
                arguments.putString(ExtraKey.EXTRA_TITLE, it)
            }
            dayOfWeekTexts?.let {
                arguments.putStringArray(ExtraKey.EXTRA_DAY_OF_WEEK_TEXTS, it)
            }
            resetText?.let {
                arguments.putString(ExtraKey.EXTRA_RESET_TEXT, it)
            }
            doneText?.let {
                arguments.putString(ExtraKey.EXTRA_DONE_TEXT, it)
            }
            amPmTexts?.let {
                arguments.putStringArray(ExtraKey.EXTRA_AM_PM_TEXTS, it)
            }
            primaryColorInt?.let {
                arguments.putInt(ExtraKey.EXTRA_PRIMARY_COLOR_INT, it)
            }
            highLightColorInt?.let {
                arguments.putInt(ExtraKey.EXTRA_HIGHLIGHT_COLOR_INT, it)
            }
            monthTitleFormatter?.let {
                FormatArgumentApplier.setMonthTitleFormatter(it)
            }
            dateResultFormatter?.let {
                FormatArgumentApplier.setDateResultFormatter(it)
            }
            timeResultFormatter?.let {
                FormatArgumentApplier.setTimeResultFormatter(it)
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