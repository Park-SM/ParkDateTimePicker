package com.smparkworld.parkdatetimepicker.ui.bottomsheet.time

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.smparkworld.parkdatetimepicker.extension.updateAssign
import com.smparkworld.parkdatetimepicker.model.SelectedTime
import com.smparkworld.parkdatetimepicker.ui.applier.FormatArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.applier.TextArgumentApplier
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.base.BaseViewModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.time.model.TimeUiModel
import java.text.SimpleDateFormat
import java.util.*

internal class TimeViewModel(
    private val timeUiModelConverter: TimeUiModelConverter = TimeUiModelConverter()
) : BaseViewModel() {

    private val _selectedTime = MutableLiveData<SelectedTime>()
    val selectedTime: LiveData<SelectedTime> get() = _selectedTime

    private val _selectedTimeUiModel = MutableLiveData<TimeUiModel>()
    val selectedTimeUiModel: LiveData<TimeUiModel> get() = _selectedTimeUiModel

    val selectedTimeTitle: LiveData<String> get() = Transformations.map(_selectedTimeUiModel) {
        FormatArgumentApplier.formatTimeTitle(it.amPm, it.hour, it.minute)
    }

    init {
        initCurrentTime()
    }

    fun onClickDone() {
        _selectedTimeUiModel.value?.let { uiModel ->
            _selectedTime.value = timeUiModelConverter.convertToSelectedTime(uiModel)
        }
    }

    fun onChangeValue(amPm: String? = null, hour: Int? = null, minute: Int? = null) {
        _selectedTimeUiModel.updateAssign {
            if (amPm != null) it.amPm = amPm
            if (hour != null) it.hour = hour
            if (minute != null) it.minute = minute
        }
    }

    private fun initCurrentTime() {
        val formatter = SimpleDateFormat("hh:mm", Locale.KOREA)
        val (hour, minute) = formatter.format(System.currentTimeMillis()).split(":").map { it.toInt() }
        val currentTime = if (hour > 12) {
            SelectedTime(
                amPm = TextArgumentApplier.getPmText(),
                hour = hour - 12,
                minute = minute
            )
        } else {
            SelectedTime(
                amPm = TextArgumentApplier.getAmText(),
                hour = hour,
                minute = minute
            )
        }
        _selectedTimeUiModel.value = timeUiModelConverter.convertToTimeUiModel(currentTime)
    }
}