package com.example.hk01.base


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import com.example.hk01.R
import com.example.hk01.util.AppManager
import com.example.hk01.widgets.TopBar
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


/**
 * 无MVP的activity基类
 * 状态栏的处理
 * 标题栏的处理
 *初始化视图
 * 注册事件
 * 初始化数据
 * 事件监听：
 */

abstract class SimpleActivity : RxAppCompatActivity() {

    protected var mContext: Activity? = null

    private var mTopBar: TopBar? = null
    protected var myContentView: View? = null

    private var mVibrator: Vibrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
        mContext = this

        initStatusBar()
        initTitleBar()
        initContentView()
//
        onViewCreated()
        registerEvent()
        initView()
        initDate()


    }


    abstract fun initView()

    ///////////////////////////////////////////标题栏相关start//////////////////////////////////////

    open fun initTitleBar() {
        if (isShowTitleBar()) {
            mTopBar = TopBar(this)
            mTopBar!!.setBackgroundColor(getTitleBarColor())
        }

    }

    private fun getTitleBarColor(): Int {
        return Color.parseColor("#ffffff")
    }

    //对标题的处理
    open fun showLeftImg() {
        mTopBar?.setLeftImage(R.drawable.icon_common_back, View.OnClickListener { finish() })
    }

    open fun showLeftImg(onClickListener: View.OnClickListener) {
        //        mTopBar.setLeftImage(R.mipmap.icon_back, onClickListener);
    }

    open fun showLeftImg(imageResouce: Int, onClickListener: View.OnClickListener) {
        mTopBar?.setLeftImage(imageResouce, onClickListener)
    }

    open fun showLeftText(leftText: String) {
        mTopBar?.setLeftText(leftText, View.OnClickListener { finish() })
    }

    open fun showLeftTextWithIcon(leftText: String, onClickListener: View.OnClickListener) {
        mTopBar!!.setLeftTextWithIcon(leftText, onClickListener)
    }

    open fun setTitle(title: String) {
        mTopBar?.setTitle(title)
    }

    override fun setTitle(resId: Int) {
        mTopBar?.setTitle(resId)
    }

    open fun showRightImg(resId: Int, onClickListener: View.OnClickListener) {
        mTopBar!!.setRightImage(resId, onClickListener)
    }

    open fun showRightImg(resId: Int) {
        mTopBar!!.setRightImage(resId)
    }

    open fun showRightText(rightText: String, onClickListener: View.OnClickListener) {
        mTopBar!!.setRightText(rightText, onClickListener)
    }


    open fun showRightText(rightText: String, color: Int, onClickListener: View.OnClickListener) {
        mTopBar!!.setRightText(rightText, color, onClickListener)
    }

    open fun setRightTextColor(color: Int) {
        mTopBar!!.setRightText(color)
    }

    open fun isShowTitleBar(): Boolean {
        return true
    }

    open fun hintBottomLine() {
        mTopBar?.hintBottomLine()
    }
    ///////////////////////////////////////////标题栏相关end//////////////////////////////////////

    /**
     * 填充contentView
     */
    open fun initContentView() {
        myContentView = View.inflate(this, getLayout(), null)
        myContentView = dealWhitStateViewAndRefreshView()
        if (mTopBar != null) {
            val layout = LinearLayout(this)
            myContentView?.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(mTopBar)
            layout.addView(myContentView)
            setContentView(layout)
        } else {
            myContentView?.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            setContentView(myContentView)
        }
    }


    abstract fun getLayout(): Int

    open fun dealWhitStateViewAndRefreshView(): View? {
        return myContentView
    }

    protected abstract fun initDate()

    /**
     * 再次加载数据
     */
    protected open fun loadDataAgain() {
        initDate()
    }

    open fun onViewCreated() {

    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

//    /////////////////////////////////////////////////状态栏相关start//////////////////////////////

    /**
     * 初始化状态栏
     */
    open fun initStatusBar() {
        showStatus()
    }

    /**
     * 隐藏状态栏
     */
    open fun hideStatus() {
        ImmersionBar.with(this)
            .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
//                .reset()
            .keyboardEnable(false)  //解决软键盘与底部输入框冲突问题
            .init()
    }

    /**
     * 显示状态栏
     */
    open fun showStatus() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
            .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
            .fitsSystemWindows(true)
            .keyboardEnable(false)  //解决软键盘与底部输入框冲突问题
            .init()
    }

    /**
     * 沉浸状态栏
     */
    open fun immersionStatus() {
        ImmersionBar.with(this)
            .transparentBar()
//                .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
            .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
//                .fitsSystemWindows(true)
            .keyboardEnable(false)  //解决软键盘与底部输入框冲突问题
            .init()
    }


    open fun getStatusBarBg(): Int {
        return Color.parseColor("#ffffff")
    }

    open fun isShowStatusBar(): Boolean {
        return true
    }
//    /////////////////////////////////////////////////状态栏相关end//////////////////////////////


    /**
     * 启动activity
     *
     * @param z
     */
    protected fun startActivity(z: Class<*>) {
        val intent = Intent(mContext, z)
        startActivity(intent)
    }


    //    注册事件
    open fun registerEvent() {
//相机权限、录音权限、读写权限、位置权限、

    }

    /**
     *
     * 刷新token，有网络请求才会
     */
    protected fun refreshToken() {

    }


    private fun getDpi(): Int {
        var dpi = 0
        val display = windowManager.defaultDisplay
        val dm = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            dpi = dm.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dpi
    }


    override fun finish() {
        hintKbTwo()
        super.finish()
    }


    //此方法只是关闭软键盘 可以在finish之前调用一下
    private fun hintKbTwo() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


}

