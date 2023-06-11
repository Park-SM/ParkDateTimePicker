package com.smparkworld.parkdatetimepicker.ui.time

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.smparkworld.parkdatetimepicker.core.DefaultOption
import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.ui.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.base.parser.extra.extras
import com.smparkworld.parkdatetimepicker.ui.time.model.TimeExtras
import com.smparkworld.parkdatetimepicker.ui.time.model.TimeUiModel
import java.text.SimpleDateFormat
import java.util.*

internal class TimeViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val extras: TimeExtras by extras(savedStateHandle)

    private val timeUiModelConverter: TimeUiModelConverter = TimeUiModelConverter()

    private val _selectedTime = MutableLiveData<TimeResult>()
    val selectedTime: LiveData<TimeResult> get() = _selectedTime

    private val _selectedTimeUiModel = MutableLiveData<TimeUiModel>()
    val selectedTimeUiModel: LiveData<TimeUiModel> get() = _selectedTimeUiModel

    private val _amPmTexts = MutableLiveData<List<String>>()
    val amPmTexts: LiveData<List<String>> get() = _amPmTexts

    init {
        initArguments()
        initCurrentTime()
    }

    fun onViewInitialized() {
        _selectedTimeUiModel.value?.let { model ->
            _selectedTime.value = timeUiModelConverter.convertToSelectedTime(model)
        }
    }

    fun onChangeValue(amPm: String? = null, hour: Int? = null, minute: Int? = null) {
        val newSelectedTime = _selectedTimeUiModel.value?.let { oldModel ->
            oldModel.copy(
                amPm = amPm ?: oldModel.amPm,
                hour = hour ?: oldModel.hour,
                minute = minute ?: oldModel.minute
            )
        } ?: return
        _selectedTime.value = timeUiModelConverter.convertToSelectedTime(newSelectedTime)
        _selectedTimeUiModel.value = newSelectedTime
    }

    private fun initArguments() {
        _amPmTexts.value = extras.amPmTexts.takeIf { it.size == 2 } ?: DefaultOption.AM_PM
    }

    private fun initCurrentTime() {
        val formatter = SimpleDateFormat("a:hh:mm", Locale.ENGLISH)
        val (amPm, hour, minute) = formatter.format(System.currentTimeMillis()).split(":")
        val currentTime = if (amPm.uppercase() == "AM") {
            TimeResult(
                amPm = extras.am ?: DefaultOption.AM,
                hour = hour.toInt(),
                minute = minute.toInt()
            )
        } else {
            TimeResult(
                amPm = extras.pm ?: DefaultOption.PM,
                hour = hour.toInt(),
                minute = minute.toInt()
            )
        }
        _selectedTimeUiModel.value = timeUiModelConverter.convertToTimeUiModel(currentTime)
    }
}