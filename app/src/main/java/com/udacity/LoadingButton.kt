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
    private var circle = RectF(0f, 0f, 0f, 0f)
    private var rotate = 0f
    private val valueAnimator = ValueAnimator()
    private var backgroundColour: Int = 0
    private var foregroundColor: Int = 0


    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Clicked -> {
                animateCircle()
            }
            ButtonState.Loading -> {
            }
            ButtonState.Completed -> {
                valueAnimator.end()
            }

        }
    }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
        color = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
    }



    init {
        isClickable = true
        val a = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, defStyleAttr, 0)
        backgroundColour = a.getColor(R.styleable.LoadingButton_backgroundColor, 0)
        foregroundColor = a.getColor(R.styleable.LoadingButton_foregroundColor, 0)
        a.recycle()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        widthSize = w
        heightSize = h

        circle.set(
            widthSize - 100f,
            heightSize / 2 - 50f,
            widthSize - 50f,
            heightSize / 2 + 50f
        )


    }

    // animate the circle to rotate around itself and the button to change color
    private fun animateCircle() {
        val propertyValuesHolder = PropertyValuesHolder.ofFloat("rotation", 0f, 360f)
        valueAnimator.setValues(propertyValuesHolder)
        valueAnimator.duration = 1000
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener {
            rotate = it.getAnimatedValue("rotation") as Float
            invalidate()
        }
        valueAnimator.start()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paint)

        // Draw the text
        paint.color = ResourcesCompat.getColor(resources, R.color.black, null)
        canvas?.drawText(
            "Download",
            widthSize / 2f,
            heightSize / 2f + (paint.descent() - paint.ascent()) / 2 - paint.descent(),
            paint
        )

        // Draw the circle and rotate it
        paint.color = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
        canvas?.save()
        canvas?.rotate(rotate, circle.centerX(), circle.centerY())
        canvas?.drawArc(circle, 0f, 360f, true, paint)
        canvas?.restore()



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