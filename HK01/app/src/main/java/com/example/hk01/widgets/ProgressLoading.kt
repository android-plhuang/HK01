package  com.example.hk01.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import com.example.hk01.R
import org.jetbrains.anko.find

/**
 * 作者： hpl
 * 创建时间： 2018/9/3
 * email： shipotianhpl@163.com
 * 作用：加载对话框
 */
class ProgressLoading private constructor(context: Context, theme: Int) : Dialog(context, theme) {

    companion object {
        private lateinit var mDialog: ProgressLoading
        private var animDrawable: AnimationDrawable? = null

        /*
            创建加载对话框
         */
        fun create(context: Context): ProgressLoading {
            //样式引入
            mDialog = ProgressLoading(context, R.style.LightProgressDialog)
            //设置布局
            mDialog.setContentView(R.layout.progress_dialog)
            mDialog.setCancelable(true)
            mDialog.setCanceledOnTouchOutside(false)
            mDialog.window.attributes.gravity = Gravity.CENTER

            val lp = mDialog.window.attributes
            lp.dimAmount = 0.2f
            //设置属性
            mDialog.window.attributes = lp

            //获取动画视图
            val loadingView = mDialog.find<ImageView>(R.id.iv_loading)
            animDrawable = loadingView.background as AnimationDrawable

            return mDialog
        }
    }

    /*
        显示加载对话框，动画开始
     */
    fun showLoading() {
        if (!isShowing){
            super.show()
            animDrawable?.start()
        }

    }

    /*
        隐藏加载对话框，动画停止
     */
    fun hideLoading() {
        super.dismiss()
        animDrawable?.stop()
    }
}