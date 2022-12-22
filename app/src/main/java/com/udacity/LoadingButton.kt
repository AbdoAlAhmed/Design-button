package com.udacity

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import kotlinx.android.synthetic.main.content_main.view.*
import kotlin.math.min
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private val valueAnimator = ValueAnimator()

    private var raduis = 0f



    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Clicked -> {
                valueAnimator.start()
            }
            ButtonState.Loading -> {
                valueAnimator.resume()
            }
            ButtonState.Completed -> {
                valueAnimator.end()
            }

        }
    }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
        color = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
    }


    init {
        isClickable = true
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthSize = w
        heightSize = h


        raduis = (min(widthSize, heightSize) / 2 * 4).toFloat()

        valueAnimator.apply {
            setValues(PropertyValuesHolder.ofFloat("progress", 0f, 1f))
            duration = 3000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                invalidate()
            }
        }




    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null)
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)

        // Draw the text
        paint.color = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
        canvas?.drawText("Download", widthSize / 2f, heightSize / 2f + (paint.descent() - paint.ascent()) / 2 - paint.descent(), paint)

        // Draw the circle
        paint.color = ResourcesCompat.getColor(resources, R.color.colorAccent, null)

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    fun setButtonStatus(buttonState: ButtonState) {
        (context as Activity).runOnUiThread {
            this.buttonState = buttonState
        }
    }
}