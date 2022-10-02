package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smparkworld.parkdatetimepicker.model.BaseListener
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.DateTimeMode
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.ToolbarStatus

internal class DateTimeViewModel : ViewModel() {

    private lateinit var mode: DateTimeMode

    private var listener: BaseListener? = null

    private val _toolbarStatus = MutableLiveData<ToolbarStatus>()
    val toolbarStatus: LiveData<ToolbarStatus> get() = _toolbarStatus

    fun init(mode: DateTimeMode?, listener: BaseListener?) {
        this.mode = mode ?: DateTimeMode.NONE
        this.listener = listener

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