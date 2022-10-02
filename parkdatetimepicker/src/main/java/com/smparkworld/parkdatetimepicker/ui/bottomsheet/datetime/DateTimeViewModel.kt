package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.ToolbarStatus

internal class DateTimeViewModel : ViewModel() {

    private lateinit var mode: DateTimeMode

    private val _toolbarStatus = MutableLiveData<ToolbarStatus>()
    val toolbarStatus: LiveData<ToolbarStatus> get() = _toolbarStatus

    fun setMode(mode: DateTimeMode?) {
        this.mode = mode ?: DateTimeMode.NONE

        when(mode) {
            DateTimeMode.TIME -> {
                _toolbarStatus.value = ToolbarStatus.TIME
            }
            else -> {
                _toolbarStatus.value = ToolbarStatus.DATE
            }
        }
    }
}