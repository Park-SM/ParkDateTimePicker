package com.smparkworld.parkdatetimepicker.ui.base.parser.extra

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.smparkworld.parkdatetimepicker.ui.date.model.DateExtras
import com.smparkworld.parkdatetimepicker.ui.date.parser.DateExtraParser
import com.smparkworld.parkdatetimepicker.ui.datetime.parser.DateTimeExtraParser
import com.smparkworld.parkdatetimepicker.ui.datetime.model.DateTimeExtras
import com.smparkworld.parkdatetimepicker.ui.time.model.TimeExtras
import com.smparkworld.parkdatetimepicker.ui.time.parser.TimeExtraParser
import kotlin.reflect.KClass

@MainThread
internal inline fun <reified T> ViewModel.extras(
    savedStateHandle: SavedStateHandle
): Lazy<T> {
    return ExtraParserProviderLazy(T::class, savedStateHandle)
}

internal class ExtraParserProviderLazy<T>(
    private val model: KClass<*>,
    private val savedStateHandle: SavedStateHandle
): Lazy<T> {

    private var cached: T? = null

    override val value: T
        get() = cached
            ?: parseExtras().also { cached = it }

    override fun isInitialized(): Boolean = (cached != null)

    @Suppress("UNCHECKED_CAST")
    private fun parseExtras(): T {
        return ExtraParserProvider.provide<T>(model).parse(savedStateHandle)
    }
}

private object ExtraParserProvider {

    private val parser: MutableMap<KClass<*>, ExtraParsable<*>> = mutableMapOf()

    init {
        parser[DateTimeExtras::class] = DateTimeExtraParser()
        parser[DateExtras::class] = DateExtraParser()
        parser[TimeExtras::class] = TimeExtraParser()
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> provide(model: KClass<*>): ExtraParsable<T> {
        return parser[model] as ExtraParsable<T>
    }
}