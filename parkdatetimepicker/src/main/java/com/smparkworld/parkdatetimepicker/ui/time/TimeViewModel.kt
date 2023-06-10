package com.smparkworld.parkdatetimepicker.ui.time

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.smparkworld.parkdatetimepicker.extension.updateAssign
import com.smparkworld.parkdatetimepicker.model.TimeResult
import com.smparkworld.parkdatetimepicker.ui.applier.FormatArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.applier.TextArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.time.model.TimeUiModel
import java.text.SimpleDateFormat
import java.util.*

internal class TimeViewModel(
    private val timeUiModelConverter: TimeUiModelConverter = TimeUiModelConverter()
) : BaseViewModel() {

    private val _selectedTime = MutableLiveData<TimeResult>()
    val selectedTime: LiveData<TimeResult> get() = _selectedTime

    private val _selectedTimeUiModel = MutableLiveData<TimeUiModel>()
    val selectedTimeUiModel: LiveData<TimeUiModel> get() = _selectedTimeUiModel

    val selectedTimeTitle: LiveData<String> get() = Transformations.map(_selectedTimeUiModel) {
        FormatArgumentApplier.formatTimeTitle(it.amPm, it.hour, it.minute)
    }

    init {
        initCurrentTime()
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

    private fun initCurrentTime() {
        val formatter = SimpleDateFormat("hh:mm", Locale.KOREA)
        val (hour, minute) = formatter.format(System.currentTimeMillis()).split(":").map { it.toInt() }
        val currentTime = if (hour > 12) {
            TimeResult(
                amPm = TextArgumentApplier.getPmText(),
                hour = hour - 12,
                minute = minute
            )
        } else {
            TimeResult(
                amPm = TextArgumentApplier.getAmText(),
                hour = hour,
                minute = minute
            )
        }
        _selectedTimeUiModel.value = timeUiModelConverter.convertToTimeUiModel(currentTime)
    }
}