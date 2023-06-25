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
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.extension.toPx


internal class RoundedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    @ColorInt
    private var validFillColor = Color.TRANSPARENT

    @ColorInt
    private var invalidFillColor = Color.TRANSPARENT

    private var radius: Float = RADIUS.toPx.toFloat()
    private var isValid = true

    init {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.PDTP_RoundedButton).apply {
                radius = getDimension(R.styleable.PDTP_RoundedButton_radius, RADIUS.toPx.toFloat())
                validFillColor = getColor(R.styleable.PDTP_RoundedButton_fillColor, Color.TRANSPARENT)
                invalidFillColor = getColor(R.styleable.PDTP_RoundedButton_invalidFillColor, Color.TRANSPARENT)
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
            color = if (isValid) validFillColor else invalidFillColor
            style = Paint.Style.FILL
        }
        canvas?.drawPath(path, paint)
        canvas?.clipPath(path)
        super.onDraw(canvas)
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

    fun setRadius(radius: Float) {
        this.radius = radius
        invalidate()
    }

    fun setFillColor(@ColorInt color: Int) {
        this.validFillColor = color
        invalidate()
    }

    fun setFillColorRGB(color: String) {
        try {
            this.validFillColor = Color.parseColor(color)
            invalidate()
        } catch (e: Exception) {
            // do nothing
        }
    }

    fun setInvalidFillColor(@ColorInt color: Int) {
        this.invalidFillColor = color
        invalidate()
    }

    fun setInvalidFillColorRGB(color: String) {
        try {
            this.invalidFillColor = Color.parseColor(color)
            invalidate()
        } catch (e: Exception) {
            // do nothing
        }
    }

    fun setValidation(isValid: Boolean) {
        this.isValid = isValid
        setRippleByValidation(isValid)
        invalidate()
    }

    private fun setRippleByValidation(isValid: Boolean) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return

        foreground = if (isValid) {
            with(TypedValue()) {
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
                ContextCompat.getDrawable(context, resourceId)
            }
        } else {
            null
        }
    }

    companion object {

        private const val RADIUS = 999
    }
}