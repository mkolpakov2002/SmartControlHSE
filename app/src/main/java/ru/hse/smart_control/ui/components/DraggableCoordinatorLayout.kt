package ru.hse.smart_control.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.customview.widget.ViewDragHelper
import ru.hse.smart_control.ui.fragments.control.UiSettingsViewModel
import java.util.*
class DraggableCoordinatorLayout @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null)
    : CoordinatorLayout(context!!, attrs) {

    interface ViewDragListener {
        fun onViewCaptured(view: View, i: Int)
        fun onViewReleased(view: View, v: Float, v1: Float)
    }

    private var uiSettingsViewModel: UiSettingsViewModel? = null
    private var isDragEnabled = false

    private val viewDragHelper: ViewDragHelper
    private val draggableChildren: MutableList<View> = ArrayList()
    private var viewDragListener: ViewDragListener? = null

    fun addDraggableChild(child: View?) {
        require(child?.parent === this)
        if (child != null) {
            draggableChildren.add(child)

        }
    }

    fun getChildrenList(): MutableList<View> {
        return draggableChildren
    }

    fun removeDraggableChild(id: Int) {
        val iterator = draggableChildren.iterator()
        while (iterator.hasNext()) {
            val child = iterator.next()
            if (child.id == id) {
                iterator.remove()
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (isDragEnabled) {
            return viewDragHelper.shouldInterceptTouchEvent(ev)
        }
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (isDragEnabled) {
            viewDragHelper.processTouchEvent(ev)
            return true
        }
        return false
    }

    fun enableDrag(enable: Boolean) {
        isDragEnabled = enable
    }

    fun setupWithViewModel(viewModel: UiSettingsViewModel) {
        uiSettingsViewModel = viewModel
    }

    private val dragCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            if (!isDragEnabled) {
                changedView.isClickable = false
            }
            uiSettingsViewModel?.setPosLeft(left)
            uiSettingsViewModel?.setPosTop(top)
        }

        override fun tryCaptureView(view: View, i: Int): Boolean {
            return view.visibility == VISIBLE && viewIsDraggableChild(view)
        }

        override fun onViewCaptured(view: View, i: Int) {
            if (viewDragListener != null) {
                viewDragListener!!.onViewCaptured(view, i)
            }
        }

        override fun onViewReleased(view: View, v: Float, v1: Float) {
            if (viewDragListener != null) {
                viewDragListener!!.onViewReleased(view, v, v1)
            }
        }

        override fun getViewHorizontalDragRange(view: View): Int {
            return view.width
        }

        override fun getViewVerticalDragRange(view: View): Int {
            return view.height
        }

        override fun clampViewPositionHorizontal(view: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(view: View, top: Int, dy: Int): Int {
            return top
        }
    }

    private fun viewIsDraggableChild(view: View): Boolean {
        return draggableChildren.isEmpty() || draggableChildren.contains(view)
    }

    fun setViewDragListener(viewDragListener: ViewDragListener?) {
        this.viewDragListener = viewDragListener
    }

    init {
        viewDragHelper = ViewDragHelper.create(this, dragCallback)
    }
}