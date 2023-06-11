package com.smparkworld.parkdatetimepicker.ui.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.extension.toDp


internal class RoundedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @ColorInt
    private var fillColor = Color.TRANSPARENT
    private var radius = RADIUS.toDp

    init {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.RoundedButton).apply {
                radius = getDimension(R.styleable.RoundedButton_radius, RADIUS.toDp)
                fillColor = getColor(R.styleable.RoundedButton_fillColor, Color.TRANSPARENT)
            }.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        val path = Path().apply {
            val rect = RectF(
                0f,
                0f,
                width.toFloat(),
                height.toFloat()
            )
            addRoundRect(rect, radius, radius, Path.Direction.CW)
        }
        val paint = Paint().apply {
            color = fillColor
            style = Paint.Style.FILL
        }
        canvas?.drawPath(path, paint)
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }

    fun setFillColor(@ColorInt color: Int) {
        this.fillColor = color
        invalidate()
    }

    fun setFillColorRGB(color: String) {
        try {
            this.fillColor = Color.parseColor(color)
            invalidate()
        } catch (e: Exception) {
            // do nothing
        }
    }

    fun setRadius(radius: Float) {
        this.radius = radius
        invalidate()
    }

    fun setDrawableRotate(degree: Float) {
        val iconDrawable = compoundDrawables.getOrNull(0) ?: return
        val iconBitmap = Bitmap.createBitmap(iconDrawable.intrinsicWidth, iconDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

        Canvas(iconBitmap).also { canvas ->
            iconDrawable.setBounds(0, 0, canvas.width, canvas.height);
            iconDrawable.draw(canvas);
        }

        val matrix = Matrix().apply {
            postRotate(degree)
        }
        val targetBitmap = Bitmap.createBitmap(iconBitmap, 0, 0, iconBitmap.width, iconBitmap.height, matrix, true)

        val drawable = BitmapDrawable(resources, targetBitmap)
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

    companion object {

        private const val RADIUS = 999f
    }
}