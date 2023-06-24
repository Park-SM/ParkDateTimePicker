package com.smparkworld.parkdatetimepicker.ui.applier

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.annotation.ColorInt
import com.smparkworld.parkdatetimepicker.ui.common.RoundedButton


internal object ColorArgumentApplier {

    @ColorInt
    private var primaryColorInt: Int? = null

    @ColorInt
    private var highLightColorInt: Int? = null

    @JvmStatic
    fun setPrimaryColorInt(@ColorInt colorInt: Int) {
        primaryColorInt = colorInt
    }

    @JvmStatic
    fun setHighLightColorInt(@ColorInt colorInt: Int) {
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
    fun applyPrimaryColor(view: RoundedButton) {
        primaryColorInt?.let { color ->
            if (view.textColors == ColorStateList.valueOf(Color.WHITE)) {
                view.setFillColor(color)
            } else {
                view.setTextColor(color)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.compoundDrawableTintList = ColorStateList.valueOf(color)
                }
            }
        }
    }

    @JvmStatic
    fun applyPrimaryColor(view: ImageView) {
        primaryColorInt?.let {
            view.imageTintList = ColorStateList.valueOf(it)
        }
    }

    @JvmStatic
    fun applyPrimaryColor(view: NumberPicker) {
        primaryColorInt?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                view.textColor = it
            }
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