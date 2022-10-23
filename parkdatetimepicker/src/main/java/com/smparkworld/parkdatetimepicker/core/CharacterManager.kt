package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.databinding.ViewHeaderFragmentDateBinding

internal object CharacterManager {

    private var dayOfWeekChars: Array<String>? = null

    @JvmStatic
    fun setDayOfWeekChars(chars: Array<String>) {
        this.dayOfWeekChars = chars
    }

    @JvmStatic
    fun applyDayOfWeekChars(headerView: ViewHeaderFragmentDateBinding) {
        dayOfWeekChars?.takeIf { it.size == 7 }?.let {
            headerView.sun.text = it[0]
            headerView.mon.text = it[1]
            headerView.tue.text = it[2]
            headerView.wed.text = it[3]
            headerView.thu.text = it[4]
            headerView.fri.text = it[5]
            headerView.sat.text = it[6]
        }
    }
}