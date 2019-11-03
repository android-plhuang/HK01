package  com.example.hk01.widgets

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.hk01.R
import com.example.hk01.base.BaseApplication
import com.example.hk01.util.DeviceUtil


/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：
 */
class TopBar(context: Context?) : FrameLayout(context) {

    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private var padding: Int = 0

    private var mLeft: ImageView? = null
    private var mLeftText: TextView? = null
    private var mTitle: TextView? = null
    private var mRight: ImageView? = null
    private var mRightText: TextView? = null
    private var mBottomView: View? = null


    init {
        initView()
    }

    private fun initView() {

        mHeight = DeviceUtil.setPX(context, 48)
        mWidth = DeviceUtil.setPX(context, 48)
        padding = DeviceUtil.setPX(context, 15)

        var params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight)
        layoutParams = params
        // TODO: 2018/1/8 背景
//        setBackgroundResource(R.mipmap.bg_topbar);
        setBackgroundColor(resources.getColor(R.color.bg_fff))
        mLeft = ImageView(context)
        mLeft?.setPadding(padding, padding, padding, padding)
        var leftParam = LayoutParams(mWidth, mHeight)
        leftParam.gravity = Gravity.LEFT
        mLeft?.layoutParams = leftParam

        mLeftText = TextView(context)
        mLeftText?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0f)
        mLeftText?.gravity = Gravity.CENTER
        mLeftText?.setTextColor(resources.getColor(R.color.txt_333_new))
        mLeftText?.setPadding(padding, 0, 0, 0)
        var leftTextParam = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mHeight)
        leftTextParam.gravity = Gravity.LEFT
        mLeftText?.layoutParams = leftTextParam

        mTitle = TextView(context)
        mTitle?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
        mTitle?.gravity = Gravity.CENTER
        mTitle?.setTextColor(resources.getColor(R.color.txt_333_new))
        mTitle?.maxWidth = DeviceUtil.setPX(context, 180)
        mTitle?.ellipsize = TextUtils.TruncateAt.MARQUEE
        mTitle?.setSingleLine(true)
        mTitle?.marqueeRepeatLimit = -1 // 跑马灯循环次数(无限循环)
        mTitle?.isFocusable = true
        mTitle?.isFocusableInTouchMode = true
        mTitle?.requestFocus()
        mTitle?.setHorizontallyScrolling(true)
        var titleParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mHeight)
        titleParams.gravity = Gravity.CENTER_HORIZONTAL
        mTitle?.layoutParams = titleParams

        mRight = ImageView(context)
        mRight?.setPadding(padding, padding, padding, padding)
        var rightParam = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mHeight)
        rightParam.gravity = Gravity.RIGHT
        mRight?.layoutParams = rightParam

        mRightText = TextView(context)
        mRightText?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0f)
        mRightText?.gravity = Gravity.CENTER
        mRightText?.setTextColor(resources.getColor(R.color.txt_333_new))
        mRightText?.setPadding(0, 0, padding, 0)
        var rightTextParam = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mHeight)
        rightTextParam.gravity = Gravity.RIGHT
        mRightText?.layoutParams = rightTextParam
//分割线
        mBottomView = View(context)
        mBottomView?.setBackgroundColor(Color.parseColor("#dedede"))
        var bottomParam = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
        bottomParam.gravity = Gravity.BOTTOM
        mBottomView?.layoutParams = bottomParam

        addView(mLeft)
        addView(mLeftText)
        addView(mTitle)
        addView(mRight)
        addView(mRightText)
        mBottomView?.let {
            addView(it)
        }

    }

    fun setLeftImage(resId: Int, listener: OnClickListener) {
        mLeft?.setImageResource(resId)
        mLeft?.setOnClickListener(listener)
    }

    fun setLeftText(leftText: String, listener: OnClickListener) {
        mLeftText?.text = leftText
        mLeftText?.setOnClickListener(listener)
    }

    fun setTitle(resId: Int) {
        setTitle(resources.getString(resId))
    }

    fun setTitle(title: String) {
        mTitle?.text = title
    }

    fun setRightImage(resId: Int, listener: OnClickListener) {
        mRight?.setImageResource(resId)
        mRight?.setOnClickListener(listener)
    }

    fun setRightImage(resId: Int) {
        mRight?.setImageResource(resId)
    }

    fun setRightText(rightText: String, listener: OnClickListener) {
        mRightText?.text = rightText
        mRightText?.setOnClickListener(listener)
    }

    fun setLeftTextWithIcon(leftText: String, listener: OnClickListener) {
        mLeftText?.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.icon_common_back), null, null, null);
        mLeftText?.compoundDrawablePadding = DeviceUtil.dp2px(BaseApplication.context, 5F)
        mLeftText?.text = leftText
        mLeftText?.setOnClickListener(listener)
    }

    fun setRightText(rightText: String, color: Int, listener: OnClickListener) {
        mRightText?.text = rightText
        mRightText?.setOnClickListener(listener)
        mRightText?.setTextColor(color)
    }
    fun setRightText( color: Int) {
        mRightText?.setTextColor(color)
    }

    fun setRightText(rightText: String) {
        mRightText?.text = rightText
    }

    fun getRightText(): String {
        return mRightText?.text.toString()
    }

    fun hideLeftImage() {
        mLeft?.visibility = View.GONE
    }

    fun hideRightImage() {
        mRight?.visibility = View.GONE
    }

    fun hide() {
        visibility = View.GONE
    }

    fun show() {
        visibility = View.VISIBLE
    }

    fun getLeftImageView(): ImageView? {
        return mLeft
    }

    fun getRightImageView(): ImageView? {
        return mRight
    }

    fun getTitleTextView(): TextView? {
        return mTitle
    }

    open fun hintBottomLine() {
        mBottomView?.visibility = View.GONE
    }
}