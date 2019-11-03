package com.example.hk01.util.recycleview

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * 带外边距和颜色所有的分割线
 */
class LineItemDecoration : RecyclerView.ItemDecoration {

    private var dividerHeight: Int = 0
    private var dividerLeft: Int = 0
    private var dividerRight: Int = 0
    private var dividerPaint: Paint? = null


    constructor(dividerHeight: Int, color: Int) : super() {
        dividerPaint = Paint()
        dividerPaint!!.color = color
        this.dividerHeight = dividerHeight
    }

    constructor(@ColorInt color: Int, dividerHeight: Int, dividerLeft: Int, dividerRight: Int) {
        dividerPaint = Paint()
        dividerPaint!!.color = color
        this.dividerHeight = dividerHeight
        this.dividerLeft = dividerLeft
        this.dividerRight = dividerRight
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until childCount - 1) {
            val view = parent.getChildAt(i)
            val top = view.bottom.toFloat()
            val bottom = (view.bottom + dividerHeight).toFloat()
            c.drawRect((left + dividerLeft).toFloat(), top, (right - dividerRight).toFloat(), bottom, dividerPaint!!)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = dividerHeight
        //        outRect.left = dividerLeft;
        //        outRect.right = dividerRight;
    }
}
