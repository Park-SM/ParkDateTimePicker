package com.smparkworld.parkdatetimepicker.ui.datetime.model

import androidx.fragment.app.Fragment
import com.smparkworld.parkdatetimepicker.ui.date.DateFragment
import com.smparkworld.parkdatetimepicker.ui.datetime.DateTimeFragment
import com.smparkworld.parkdatetimepicker.ui.time.TimeFragment
import kotlin.reflect.KClass

internal enum class Phase(
    private val fragment: KClass<out Fragment>
) {
    INIT(Nothing::class),
    DATE(DateFragment::class),
    TIME(TimeFragment::class),
    DONE(Nothing::class);

    fun createFragment(): Fragment? {
        return when(this.fragment) {
            DateTimeFragment::class -> DateTimeFragment()
            DateFragment::class -> DateFragment()
            TimeFragment::class -> TimeFragment()
            else -> null
        }
    }

    fun getFragmentTag(): String? = this.fragment.simpleName
}