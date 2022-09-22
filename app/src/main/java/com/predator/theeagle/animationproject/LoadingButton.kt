package com.predator.theeagle.animationproject

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var buttonBackgroundColor = 0
    private var buttonTextColor = 0
    private var buttonLoadingColor = 0
    private var buttonCircleColor = 0
    private var buttonTextStr = ""
    private var progress = 0
    private val valueAnimator = ValueAnimator.ofInt(0, 360).setDuration(2000)

    var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { _, _, new ->

        when (new) {
            ButtonState.Loading -> {
                buttonTextStr = resources.getString(R.string.button_loading)

                valueAnimator.start()

            }
            ButtonState.Completed -> {
                buttonTextStr = resources.getString(R.string.button_name)
                valueAnimator.cancel()

                progress = 0
            }
            else -> {return@observable}
        }

        invalidate()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 100.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            buttonBackgroundColor = getColor(R.styleable.LoadingButton_backgroundColor, 0)
            buttonTextColor = getColor(R.styleable.LoadingButton_textColor, 0)
            buttonLoadingColor = getColor(R.styleable.LoadingButton_buttonLoadingColor, 0)
            buttonCircleColor = getColor(R.styleable.LoadingButton_buttonCircleColor, 0)
        }
        buttonState = ButtonState.Completed
        valueAnimator.apply {
            addUpdateListener {
                progress = it.animatedValue as Int
                invalidate()
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val width: Int = resolveSizeAndState(minWidth, widthMeasureSpec, 1)
        val height: Int = resolveSizeAndState(
            MeasureSpec.getSize(width),
            heightMeasureSpec,
            0
        )
        widthSize = width
        heightSize = height
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.color = buttonBackgroundColor
        canvas?.drawRect(0f,0f,widthSize.toFloat(), heightSize.toFloat(), paint)

        paint.color = buttonLoadingColor
        canvas?.drawRect(0f, 0f, widthSize * progress/360f, heightSize.toFloat(), paint)

        paint.color = buttonTextColor
        paint.textSize=66f
        canvas?.drawText(buttonTextStr, widthSize/2.0f, heightSize/2.0f+20 , paint)

        paint.color = buttonCircleColor
        canvas?.drawArc(widthSize - 200f,75f,widthSize - 100f,120f,0f, progress.toFloat(), true, paint)
    }
}