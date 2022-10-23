package com.smparkworld.parkdatetimepicker.core

import com.smparkworld.parkdatetimepicker.databinding.ViewHeaderFragmentDateBinding
import com.smparkworld.parkdatetimepicker.databinding.ViewHeaderFragmentTimeBinding

internal object CharacterManager {

    private var dayOfWeekTexts: Array<String>? = null

    private var timeDoneText: String? = null

    @JvmStatic
    fun setDayOfWeekTexts(texts: Array<String>) {
        this.dayOfWeekTexts = texts
    }

    @JvmStatic
    fun setTimeDoneText(text: String) {
        this.timeDoneText = text
    }

    @JvmStatic
    fun applyDayOfWeekTexts(headerView: ViewHeaderFragmentDateBinding) {
        dayOfWeekTexts?.takeIf { it.size == 7 }?.let {
            headerView.sun.text = it[0]
            headerView.mon.text = it[1]
            headerView.tue.text = it[2]
            headerView.wed.text = it[3]
            headerView.thu.text = it[4]
            headerView.fri.text = it[5]
            headerView.sat.text = it[6]
        }
    }

    @JvmStatic
    fun applyTimeDoneText(headerView: ViewHeaderFragmentTimeBinding) {
        timeDoneText?.let {
            headerView.done.text = it
        }
    }
}