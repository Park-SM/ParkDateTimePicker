package com.smparkworld.parkdatetimepicker.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.databinding.PdtpViewCircleButtonBinding

internal class CircleButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding = PdtpViewCircleButtonBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var text: String = ""
        set(value) {
            field = onChangeText(value)
        }

    fun setTextColor(@ColorInt color: Int) {
        binding.title.setTextColor(color)
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        binding.container.setCardBackgroundColor(color)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        binding.container.setOnClickListener(listener)
    }

    private fun onChangeText(value: String): String {
        binding.title.text = value
        return value
    }
}