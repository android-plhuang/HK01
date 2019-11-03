package com.example.hk01.util.recyclerview

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorInt
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

/**
 * 四周包围GridDecoration
 */
class GridDividerItemDecoration(private val mDividerWidth: Int, @ColorInt color: Int) : RecyclerView.ItemDecoration() {
    private val mPaint: Paint?

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = color
        mPaint.style = Paint.Style.FILL
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state!!)
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter?.itemCount

        val isLastRow = isLastRow(parent, itemPosition, spanCount, childCount!!)
        val isfirstRow = isfirstRow(parent, itemPosition, spanCount, childCount)

        val top: Int
        val left: Int
        val right: Int
        val bottom: Int = mDividerWidth
        val eachWidth = (spanCount - 1) * mDividerWidth / spanCount
        val dl = mDividerWidth - eachWidth

        left = itemPosition % spanCount * dl
        right = eachWidth - left
        //Log.e("zzz", "itemPosition:" + itemPosition + " |left:" + left + " right:" + right + " bottom:" + bottom);
        //        if (isLastRow) {
        //            bottom = 0;
        //        }
        if (isfirstRow) {
            top = (spanCount - 1) * mDividerWidth / spanCount
        } else {
            top = 0
        }
        outRect.set(left, top, right, bottom)

    }

   /* override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDraw(c, parent, state!!)
        draw(c, parent)
    }*/

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        draw(c, parent)
    }

    //绘制横向 item 分割线
    private fun draw(canvas: Canvas, parent: RecyclerView) {
        val childSize = parent.childCount
        for (i in 0 until childSize) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams

            //画水平分隔线
            var left = child.left
            var right = child.right
            var top = child.bottom + layoutParams.bottomMargin
            var bottom = top + mDividerWidth
            if (mPaint != null) {
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }
            //画垂直分割线
            top = child.top
            bottom = child.bottom + mDividerWidth
            left = child.right + layoutParams.rightMargin
            right = left + mDividerWidth
            if (mPaint != null) {
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
            }
        }
    }

    private fun isLastColumn(parent: RecyclerView, pos: Int, spanCount: Int,
                             childCount: Int): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {// 如果是最后一列，则不需要绘制右边
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                    .orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)
                // 如果是最后一列，则不需要绘制右边
                {
                    return true
                }
            } else {
                childCount -= childCount % spanCount
                if (pos >= childCount)
                // 如果是最后一列，则不需要绘制右边
                    return true
            }
        }
        return false
    }

    private fun isLastRow(parent: RecyclerView, pos: Int, spanCount: Int,
                          childCount: Int): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            // childCount = childCount - childCount % spanCount;
            val lines = if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
            return lines == pos / spanCount + 1
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                    .orientation
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount -= childCount % spanCount
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true
            } else {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true
                }
            }
        }
        return false
    }

    private fun isfirstRow(parent: RecyclerView, pos: Int, spanCount: Int,
                           childCount: Int): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            // childCount = childCount - childCount % spanCount;
            val lines = if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
            //如是第一行则返回true
            return pos / spanCount + 1 == 1
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                    .orientation
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount -= childCount % spanCount
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true
            } else {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true
                }
            }
        }
        return false
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        // 列数
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {

            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager
                    .spanCount
        }
        return spanCount
    }
}
