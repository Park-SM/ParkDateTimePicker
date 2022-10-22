package com.smparkworld.parkdatetimepicker.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.databinding.ViewCircleButtonBinding

class CircleButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding = ViewCircleButtonBinding.inflate(
        LayoutInflater.from(context), this, true
    )

//    private val checkedBackColor = ContextCompat.getColor(context, R.color.black)
//    private val uncheckedBackColor = ContextCompat.getColor(context, R.color.gray_bright_white)
//    private val checkedFontColor = ContextCompat.getColor(context, R.color.white)
//    private val uncheckedFontColor = ContextCompat.getColor(context, R.color.black)

//    private var isChecked = false
//        set(value) {
//            field = onChangeIsChecked(value)
//        }

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
        binding.container.setOnClickListener {
            listener?.onClick(it)
//            isChecked = !isChecked
        }
    }

//    private fun onChangeIsChecked(value: Boolean): Boolean {
//        binding.container.setCardBackgroundColor(if (value) checkedBackColor else uncheckedBackColor)
//        binding.title.setTextColor(if (value) checkedFontColor else uncheckedFontColor)
//        return value
//    }

    private fun onChangeText(value: String): String {
        binding.title.text = value
        return value
    }
}