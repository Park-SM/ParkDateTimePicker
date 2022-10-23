package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model

import androidx.fragment.app.Fragment
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.DateFragment
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.date.range.DateRangeFragment
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.DateTimeFragment
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.TimeFragment
import kotlin.reflect.KClass

internal enum class Phase(val fragment: KClass<out Fragment>) {
    INIT(Nothing::class),
    DATE(DateFragment::class),
    TIME(TimeFragment::class),
    DATE_RANGE_FIRST(DateRangeFragment::class),
    DATE_RANGE_SECOND(DateRangeFragment::class),
    TIME_RANGE_FIRST(TimeFragment::class),
    TIME_RANGE_SECOND(TimeFragment::class),
    DONE(Nothing::class);

    fun createFragment(): Fragment? {
        return when(this.fragment) {
            DateTimeFragment::class -> DateTimeFragment()
            DateFragment::class -> DateFragment()
            TimeFragment::class -> TimeFragment()
            DateRangeFragment::class -> DateRangeFragment()
            else -> null
        }
    }

    fun getFragmentTag(): String? = this.fragment.simpleName
}