package com.smparkworld.parkdatetimepicker.ui.applier

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import com.smparkworld.parkdatetimepicker.ui.CircleButton

internal object ColorArgumentApplier {

    @ColorInt
    private var primaryColorInt: Int? = null

    @ColorInt
    private var highLightColorInt: Int? = null

    @JvmStatic
    fun setPrimaryColor(@ColorInt colorInt: Int) {
        primaryColorInt = colorInt
    }

    @JvmStatic
    fun setHighLightColor(@ColorInt colorInt: Int) {
        highLightColorInt = colorInt
    }

    @JvmStatic
    fun applyPrimaryColor(view: TextView) {
        primaryColorInt?.let {
            view.setTextColor(it)
        }
    }

    @JvmStatic
    fun applyPrimaryColor(view: TextView, options: Options) {
        primaryColorInt?.let {
            when(options) {
                Options.BACKGROUND_TINT -> {
                    view.backgroundTintList = ColorStateList.valueOf(it)
                }
                else -> {
                    view.setTextColor(it)
                }
            }
        }
    }

    @JvmStatic
    fun applyPrimaryColor(view: CircleButton) {
        primaryColorInt?.let {
            view.setBackgroundColor(it)
        }
    }

    @JvmStatic
    fun applyPrimaryColor(view: ImageView) {
        primaryColorInt?.let {
            view.imageTintList = ColorStateList.valueOf(it)
        }
    }

    @JvmStatic
    fun applyHighLightColor(view: TextView) {
        highLightColorInt?.let {
            view.setTextColor(it)
        }
    }

    enum class Options {
        DEFAULT,
        BACKGROUND_TINT
    }
}