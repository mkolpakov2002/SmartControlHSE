package ru.hse.smart_control.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import ru.hse.smart_control.R

@RequiresApi(Build.VERSION_CODES.M)
class JoystickView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    interface OnJoystickMoveListener {
        fun onValueChanged(xValue: Float, yValue: Float, angle: Float)
    }

    private val outerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val innerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val handlePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var centerX = 0f
    private var centerY = 0f
    private var outerRadius = 0f
    private var innerRadius = 0f
    private var handleRadius = 0f
    private var handleX = 0f
    private var handleY = 0f
    private var joystickMoveListener: OnJoystickMoveListener? = null
    var angle = 0.0
    init {
        outerCirclePaint.color = context.getColor(R.color.joystick_primary_color)
        innerCirclePaint.color = Color.TRANSPARENT
        handlePaint.color = context.getColor(R.color.joystick_secondary_color)
    }

    fun setOnJoystickMoveListener(listener: OnJoystickMoveListener) {
        joystickMoveListener = listener
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        outerRadius = minOf(w, h) / 2f
        innerRadius = outerRadius * 0.8f
        handleRadius = outerRadius * 0.25f
        handleX = centerX
        handleY = centerY
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX, centerY, outerRadius, outerCirclePaint)
        canvas.drawCircle(centerX, centerY, innerRadius, innerCirclePaint)
        canvas.drawCircle(handleX, handleY, handleRadius, handlePaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE
            -> {
                angle = Math.toDegrees(Math.atan2((event.y - centerY).toDouble(), (event.x - centerX).toDouble()))

                val radius = outerRadius - handleRadius
                handleX = (centerX + radius * Math.cos(Math.toRadians(angle))).toFloat()
                handleY = (centerY + radius * Math.sin(Math.toRadians(angle))).toFloat()
                invalidate()
                joystickMoveListener?.onValueChanged((handleX - centerX) / outerRadius, (handleY - centerY) / outerRadius, angle.toFloat())
                return true
            }
            MotionEvent.ACTION_UP -> {
                handleX = centerX
                handleY = centerY
                invalidate()
                joystickMoveListener?.onValueChanged(0f, 0f, angle.toFloat())
                return true
            }
        }
        return false
    }
}
