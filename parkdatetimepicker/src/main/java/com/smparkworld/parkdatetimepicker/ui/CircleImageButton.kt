package com.smparkworld.parkdatetimepicker.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.smparkworld.parkdatetimepicker.R

class CircleImageButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = R.attr.circleImageButtonStyle
) : AppCompatImageButton(context, attrs, defStyleAttrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.clipPath(getPath())
    }

    private fun getPath(): Path {
        return Path().apply {
            val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
            val floats = FloatArray(8).also { array ->
                repeat(8) { i ->
                    array[i] = RADIUS
                }
            }
            addRoundRect(rect, floats, Path.Direction.CW)
        }
    }

    companion object {

        private const val RADIUS = 9999f
    }
}