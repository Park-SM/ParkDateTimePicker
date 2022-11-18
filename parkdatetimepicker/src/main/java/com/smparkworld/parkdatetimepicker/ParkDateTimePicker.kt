package com.smparkworld.parkdatetimepicker

import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.core.ExtraKey
import com.smparkworld.parkdatetimepicker.model.formatter.DateTitleFormatter
import com.smparkworld.parkdatetimepicker.model.formatter.TimeTitleFormatter
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
        private var titleResId: Int? = null

        private var dayOfWeekTexts: Array<String>? = null

        private var timeDoneText: String? = null

        private var amPmTexts: Array<String>? = null

        private var primaryColorCode: String? = null
        private var primaryColorResId: Int? = null

        private var highLightColorCode: String? = null
        private var highLightColorResId: Int? = null

        private var dateTitleFormatter: DateTitleFormatter? = null
        private var timeTitleFormatter: TimeTitleFormatter? = null

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

        override fun setTitle(@StringRes titleResId: Int): ParkDateTimePickerBuilder {
            this.titleResId = titleResId
            return this
        }

        override fun setDayOfWeekTexts(texts: Array<String>): ParkDateTimePickerBuilder {
            this.dayOfWeekTexts = texts
            return this
        }

        override fun setTimeDoneText(text: String): ParkDateTimePickerBuilder {
            this.timeDoneText = text
            return this
        }

        override fun setAmPmTexts(texts: Array<String>): ParkDateTimePickerBuilder {
            this.amPmTexts = texts
            return this
        }

        override fun setPrimaryColor(colorCode: String): ParkDateTimePickerBuilder {
            this.primaryColorCode = colorCode
            return this
        }

        override fun setPrimaryColor(@ColorInt colorResId: Int): ParkDateTimePickerBuilder {
            this.primaryColorResId = colorResId
            return this
        }

        override fun setHighLightColor(colorCode: String): ParkDateTimePickerBuilder {
            this.highLightColorCode = colorCode
            return this
        }

        override fun setHighLightColor(colorResId: Int): ParkDateTimePickerBuilder {
            this.highLightColorResId = colorResId
            return this
        }

        override fun setDateTitleFormatter(formatter: DateTitleFormatter): ParkDateTimePickerBuilder {
            this.dateTitleFormatter = formatter
            return this
        }

        override fun setTimeTitleFormatter(formatter: TimeTitleFormatter): ParkDateTimePickerBuilder {
            this.timeTitleFormatter = formatter
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
            dayOfWeekTexts?.let {
                arguments.putStringArray(ExtraKey.EXTRA_DAY_OF_WEEK_TEXTS, it)
            }
            timeDoneText?.let {
                arguments.putString(ExtraKey.EXTRA_TIME_DONE_TEXT, it)
            }
            amPmTexts?.let {
                arguments.putStringArray(ExtraKey.EXTRA_AM_PM_TEXTS, it)
            }
            primaryColorCode?.let {
                arguments.putString(ExtraKey.EXTRA_PRIMARY_COLOR_CODE, it)
            }
            primaryColorResId?.let {
                arguments.putInt(ExtraKey.EXTRA_PRIMARY_COLOR_RES_ID, it)
            }
            highLightColorCode?.let {
                arguments.putString(ExtraKey.EXTRA_HIGHLIGHT_COLOR_CODE, it)
            }
            highLightColorResId?.let {
                arguments.putInt(ExtraKey.EXTRA_HIGHLIGHT_COLOR_RES_ID, it)
            }
            dateTitleFormatter?.let {
                FormatArgumentApplier.setDateTitleFormatter(it)
            }
            timeTitleFormatter?.let {
                FormatArgumentApplier.setTimeTitleFormatter(it)
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