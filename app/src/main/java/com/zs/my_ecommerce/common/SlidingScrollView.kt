package com.zs.my_ecommerce.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import kotlin.math.abs


class SlidingScrollView : HorizontalScrollView {
    private var startX = 0f
    private var startY = 0f
    private var isScrolling = false
    private var buttonWidth = 0
    var isExpanded: Boolean = false
        private set

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setOverScrollMode(OVER_SCROLL_NEVER)
        setHorizontalScrollBarEnabled(false)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        // 获取按钮区域宽度
        if (getChildCount() > 0) {
            val innerLayout = getChildAt(0) as ViewGroup
            if (innerLayout.getChildCount() > 1) {
                buttonWidth = innerLayout.getChildAt(1).getWidth()
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.getX()
                startY = ev.getY()
                isScrolling = false
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = abs((ev.getX() - startX).toDouble()).toFloat()
                val dy = abs((ev.getY() - startY).toDouble()).toFloat()


                // 水平滑动距离大于垂直滑动距离时拦截事件
                if (dx > dy && dx > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    isScrolling = true
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (ev.getAction() == MotionEvent.ACTION_UP && isScrolling) {
            smoothScrollToThreshold()
            return true
        }
        return super.onTouchEvent(ev)
    }

    private fun smoothScrollToThreshold() {
        val scrollX = getScrollX()


        // 如果按钮宽度未获取到，使用默认值
        if (buttonWidth == 0) {
            buttonWidth = (120 * getResources().getDisplayMetrics().density).toInt()
        }

        if (scrollX >= buttonWidth / 2) {
            expandButtons()
        } else {
            collapseButtons()
        }
    }

    fun expandButtons() {
        smoothScrollTo(buttonWidth, 0)
        isExpanded = true
    }

    fun collapseButtons() {
        smoothScrollTo(0, 0)
        isExpanded = false
    }
}