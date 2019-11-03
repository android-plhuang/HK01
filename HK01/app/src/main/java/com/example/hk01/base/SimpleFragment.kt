package  com.example.hk01.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.hk01.R
import com.example.hk01.widgets.TopBar
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.disposables.Disposable
import java.lang.reflect.Field


/**
 * 无MVP的fragment基类
 * 状态栏的处理
 * 标题栏的处理
 * 初始化视图
 * 注册事件
 * 初始化数据
 *
 * 可不可以使用动态代理来完成呢？
 */
abstract class SimpleFragment : RxFragment() {

    lateinit var mView: View
    var mContext: Context? = null
    var mTopBar: TopBar? = null
    private var mStatusBarView:View?=null

    protected abstract val layout: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        mView = inflater.inflate(layout, null)
        mContext = context
        initStatusBar()
        initTitleBar()
        initContentView(inflater, container, savedInstanceState)

        return initContentView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = context
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewCreated()
        registerEvent()
        initView()
        initDate()
    }
    //======================================状态栏========================================

    /**
     * 初始化状态栏
     */
    open fun initStatusBar() {
        if (isShowStatusBar()) {
            //设置为白底黑字

        }
    }

    /**
     * 6.0以上为白色背景，6.0一下为灰色
     */
    open fun getStatusBarBg(): Int {
        if (android.os.Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            return Color.parseColor("#ffffff")
        }else{
            return Color.GRAY
        }

    }

    open fun isShowStatusBar(): Boolean {
        return true
    }


    open fun initTitleBar() {
        if (isShowTitleBar()) {
            mTopBar = TopBar(mContext)
            mTopBar!!.setBackgroundColor(getTitleBarColor())
        }

    }

    private fun getTitleBarColor(): Int {
        return Color.parseColor("#ffffff")
    }

    //对标题的处理
    open fun showLeftImg() {
        mTopBar?.setLeftImage(R.mipmap.ic_launcher, View.OnClickListener { activity?.finish() })
    }

    open fun showLeftImg(onClickListener: View.OnClickListener) {
        //        mTopBar.setLeftImage(R.mipmap.icon_back, onClickListener);
    }

    open fun showLeftImg(imageResouce: Int, onClickListener: View.OnClickListener) {
        mTopBar?.setLeftImage(imageResouce, onClickListener)
    }

    open fun showLeftText(leftText: String) {
        mTopBar?.setLeftText(leftText, View.OnClickListener { activity?.finish() })
    }

    open fun setTitle(title: String) {
        mTopBar?.setTitle(title)
    }

    open fun setTitle(resId: Int) {
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

    open fun isShowTitleBar(): Boolean {
        return true
    }
    ///////////////////////////////////////////标题栏相关end//////////////////////////////////////


    /**
     * 填充contentView
     */
    open fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        val inflater = LayoutInflater.from(context)
        mView = inflater.inflate(layout, container, false)
//        mView =inflater.inflate(layout,container,false)
//        mView = inflater.inflate(layout, null)
        mView = dealWhitStateViewAndRefreshView()!!
        if (mTopBar != null) {
            val tempLayout = LinearLayout(mContext)
            tempLayout?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            tempLayout.orientation = LinearLayout.VERTICAL

          addStatusBarView(tempLayout)


            if (mView?.parent != null && mView?.parent is ViewGroup) {
                (mView?.parent as ViewGroup)?.removeView(mView)
            }
            if (mTopBar?.parent != null && mTopBar?.parent is ViewGroup) {
                (mTopBar?.parent as ViewGroup)?.removeView(mTopBar)
            }
            tempLayout.addView(mTopBar)
            tempLayout.addView(mView)
            return tempLayout
        } else {
            if (isShowStatusBar()){
                val tempLayout = LinearLayout(mContext)
                tempLayout?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                tempLayout.orientation = LinearLayout.VERTICAL
               addStatusBarView(tempLayout)
                if (mView?.parent != null && mView?.parent is ViewGroup) {
                    (mView?.parent as ViewGroup)?.removeView(mView)
                }
                tempLayout.addView(mView)
                return tempLayout
            }else{
                mView?.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                return mView
            }

        }
    }

    /**
     * 手动添加状态栏的view，由于动态切换会出现抖动的现象
     */
    private fun addStatusBarView(tempLayout: LinearLayout) {
        if (!isShowStatusBar()) return
         mStatusBarView = View(context)
        var params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight())
        mStatusBarView?.layoutParams = params
        mStatusBarView?.setBackgroundColor(getStatusBarBg())
        if (mStatusBarView?.parent != null && mStatusBarView?.parent is ViewGroup) {
            (mStatusBarView?.parent as ViewGroup)?.removeView(mStatusBarView)
        }
        tempLayout.addView(mStatusBarView)
    }

    /**
     * 这是一个辅助方法，有时候isShowStatusBar返回false还是显示状态栏
     */
    open fun hintStatusBarView(isHint:Boolean){
        mStatusBarView?.visibility = if (isHint) View.GONE else View.VISIBLE
    }

    open fun registerEvent() {

    }


    open fun dealWhitStateViewAndRefreshView(): View? {
        return mView
    }

    /**
     * view创建完成之后的一些初始化操作，初始化对话框，一些依赖注入等，在baseFragment完成
     */
    open fun initViewCreated() {

    }


    protected abstract fun initDate()

    protected abstract fun initView()


    protected fun startActivity(z: Class<*>) {
        val intent = Intent(mContext, z)
        startActivity(intent)
    }

    open fun getStatusBarHeight(): Int {
        var c: Class<*>? = null

        var obj: Any? = null

        var field: Field? = null

        var x = 0
        var sbar = 0

        try {

            c = Class.forName("com.android.internal.R\$dimen")

            obj = c!!.newInstance()

            field = c.getField("status_bar_height")

            x = Integer.parseInt(field!!.get(obj).toString())

            sbar = context!!.resources.getDimensionPixelSize(x)

        } catch (e1: Exception) {

            e1.printStackTrace()

        }

        return sbar
    }

    protected fun dispose(dis: Disposable?){
        if (dis!=null && !dis!!.isDisposed){
            dis.dispose()
        }
    }

    open fun firstLoadData(){

    }

}
