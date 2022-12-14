package com.smparkworld.parkdatetimepicker.ui.applier

import android.widget.NumberPicker
import android.widget.TextView
import com.smparkworld.parkdatetimepicker.databinding.PdtpViewHeaderFragmentDateBinding
import com.smparkworld.parkdatetimepicker.databinding.PdtpViewHeaderFragmentTimeBinding

internal object TextArgumentApplier {

    private var title: String = "Calendar"

    private var dayOfWeekTexts: Array<String> = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    private var timeDoneText: String = "Done"

    private var amPmTexts: Array<String> = arrayOf("AM", "PM")

    @JvmStatic
    fun setTitle(text: String) {
        title = text
    }

    @JvmStatic
    fun setDayOfWeekTexts(texts: Array<String>) {
        texts.takeIf { it.size == 7 }?.let {
            dayOfWeekTexts = it
        }
    }

    @JvmStatic
    fun setTimeDoneText(text: String) {
        timeDoneText = text
    }

    @JvmStatic
    fun setAmPmTexts(texts: Array<String>) {
        texts.takeIf { it.size == 2 }?.let {
            amPmTexts = it
        }
    }

    @JvmStatic
    fun applyTitle(view: TextView) {
        view.text = title
    }

    @JvmStatic
    fun applyDayOfWeekTexts(headerView: PdtpViewHeaderFragmentDateBinding) {
        headerView.sun.text = dayOfWeekTexts[0]
        headerView.mon.text = dayOfWeekTexts[1]
        headerView.tue.text = dayOfWeekTexts[2]
        headerView.wed.text = dayOfWeekTexts[3]
        headerView.thu.text = dayOfWeekTexts[4]
        headerView.fri.text = dayOfWeekTexts[5]
        headerView.sat.text = dayOfWeekTexts[6]
    }

    @JvmStatic
    fun applyTimeDoneText(headerView: PdtpViewHeaderFragmentTimeBinding) {
        headerView.done.text = timeDoneText
    }

    @JvmStatic
    fun applyAmPmTexts(numberPicker: NumberPicker) {
        numberPicker.displayedValues = amPmTexts
    }

    @JvmStatic
    fun getAmText(): String {
        return amPmTexts[0]
    }

    fun getPmText(): String {
        return amPmTexts[1]
    }
}