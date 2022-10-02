package com.smparkworld.parkdatetimepicker.core

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt

object ColorManager {

    @ColorInt
    private var textColorInt: Int? = null

    @JvmStatic
    fun setTextColor(@ColorInt colorInt: Int) {
        textColorInt = colorInt
    }

    @JvmStatic
    fun applyTextColor(view: TextView) {
        textColorInt?.let {
            view.setTextColor(it)
        }
    }

    @JvmStatic
    fun applyImageTint(view: ImageView) {
        textColorInt?.let {
            view.imageTintList = ColorStateList.valueOf(it)
        }
    }
}