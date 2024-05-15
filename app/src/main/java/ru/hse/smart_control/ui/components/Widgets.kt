package ru.hse.smart_control.ui.components

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider
import ru.hse.smart_control.R


private fun createCardView(context: Context): CardView {
    val cardView = CardView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setContentPadding(16, 16, 16, 16)
        cardElevation = 8f
    }
    return cardView
}

private fun createLinearLayout(context: Context): LinearLayout {
    val linearLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
    return linearLayout
}

private fun createTextView(context: Context, tvLabel: String): TextView {
    val textView = TextView(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        text = tvLabel
    }
    textView.isClickable = true
    textView.setOnClickListener {
        Toast.makeText(context, "txt", Toast.LENGTH_SHORT).show()
    }
    return textView
}

private fun dpToPx(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

fun Context.createButton(
    container: ViewGroup, title: String,
    tvLabel: String,
    onClick: () -> Unit
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this, tvLabel)

    val button = Button(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
    button.id = View.generateViewId()
    button.text = title
    button.setTextColor(Color.WHITE)
    button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark))
    button.setOnClickListener {
        onClick()
    }

    linearLayout.addView(textView)
    linearLayout.addView(button)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createSwitch(
    container: ViewGroup,
    tvLabel: String,
    onClick: (Boolean) -> Unit
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this, tvLabel)

    val switch = SwitchCompat(this)
    Log.d("TAG", "switch id: ${switch.id}")
    switch.setOnCheckedChangeListener { buttonView, isChecked ->
        onClick(isChecked)
    }
    switch.isClickable = true

    val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    layoutParams.gravity = Gravity.CENTER
    switch.layoutParams = layoutParams

    linearLayout.addView(textView)
    linearLayout.addView(switch)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createEditTextWithButton(
    container: ViewGroup,
    tvLabel: String,
) : View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this, tvLabel)

    val editText = EditText(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    val button = Button(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        text = "Выполнить"
    }

    linearLayout.addView(textView)
    linearLayout.addView(editText)
    linearLayout.addView(button)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createSlider(
    container: ViewGroup,
    valueFrom: Int,
    valueTo: Int,
    step: Int,
    widthInDp: Int = 100,
    tvLabel: String,
    onClick: (Int) -> Unit
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this, tvLabel)

    val slider = Slider(this)
    slider.id = View.generateViewId()
    slider.stepSize = step.toFloat()
    slider.valueFrom = valueFrom.toFloat()
    slider.valueTo = valueTo.toFloat()
    slider.value = "3000".toFloat()

    val layoutParams = LinearLayout.LayoutParams(
        dpToPx(this, widthInDp),
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    slider.layoutParams = layoutParams

    slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{

        override fun onStartTrackingTouch(slider: Slider) {

        }

        override fun onStopTrackingTouch(slider: Slider) {
            onClick(slider.value.toInt())
        }
    })

    linearLayout.addView(textView)
    linearLayout.addView(slider)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createCheckbox(
    container: ViewGroup,
    tvLabel: String,
    onClick: (Boolean) -> Unit
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this, tvLabel)

    val checkbox = CheckBox(this)
    checkbox.id = View.generateViewId()
    checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
        onClick(isChecked)
    }

    linearLayout.addView(textView)
    linearLayout.addView(checkbox)
    cardView.addView(linearLayout)

    container.addView(cardView)
    return cardView
}

fun Context.createColorPicker(
    container: ViewGroup,
    width: Int = 100,
    height: Int = 100,
    tvLabel: String,
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this, tvLabel)

    val colorPicker = ImageView(this)
    colorPicker.id = View.generateViewId()

    colorPicker.setImageResource(R.drawable.color)
    val params = ViewGroup.LayoutParams(
        dpToPx(this, width),
        dpToPx(this, height)
    )
    colorPicker.layoutParams = params

    val view = View(this)
    val paramsView = ViewGroup.LayoutParams(
        dpToPx(this, 20),
        dpToPx(this, 20)
    )
    view.layoutParams = paramsView

    linearLayout.addView(textView)
    linearLayout.addView(view)
    linearLayout.addView(colorPicker)
    cardView.addView(linearLayout)

    container.addView(cardView)

    return cardView
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.createJoystick(
    container: ViewGroup,
//    width: Int,
//    height: Int,
    tvLabel: String,
    onClick: (Float) -> Unit
): View {
    val cardView = createCardView(this)
    cardView.id = View.generateViewId()
    val linearLayout = createLinearLayout(this)
    val textView = createTextView(this, tvLabel)

    val joystick = JoystickView(this, null)
    joystick.id = View.generateViewId()

    joystick.setOnJoystickMoveListener(object: JoystickView.OnJoystickMoveListener {
        override fun onValueChanged(xValue: Float, yValue: Float, angle: Float) {
            onClick(angle)
//            Log.d("Joystick", "xValue: $xValue, yValue: $yValue, angle: $angle")
        }
    })

//    val params = ViewGroup.LayoutParams(
//        dpToPx(this, width),
//        dpToPx(this, height)
//    )
//    joystick.layoutParams = params

    val view = View(this)
    val paramsView = ViewGroup.LayoutParams(
        dpToPx(this, 20),
        dpToPx(this, 20)
    )
    view.layoutParams = paramsView

    linearLayout.addView(textView)
    linearLayout.addView(view)
    linearLayout.addView(joystick)
    cardView.addView(linearLayout)

    container.addView(cardView)

    return cardView
}


