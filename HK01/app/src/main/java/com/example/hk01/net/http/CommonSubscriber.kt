package  com.example.hk01.net.http

import android.util.Log
import com.example.hk01.base.BaseView
import com.example.hk01.data.bean.TestBean
import com.example.hk01.util.LogUtil
import io.reactivex.subscribers.ResourceSubscriber
import retrofit2.HttpException

/**
 * 作者： hpl
 * 创建时间： 2018/9/3
 * email： shipotianhpl@163.com
 * 作用：统一处理返回结果
 * 显示加载动画
 * 显示加载完成后的信息提示
 */
open abstract class CommonSubscriber<T : TestBean> constructor(
    var mView: BaseView,
    val isShowHintStr: Boolean = true,
    val isShowLoading: Boolean = true
) : ResourceSubscriber<T>() {

    //constructor()
    override fun onNext(t: T) {
        if (mView == null) {
            Log.e("onNext", "mView==null")
        } else {
            Log.e("onNext", "mView!=null")
        }
        mView.let {
            it.hintLoading()
            handleData(t)
        }
        mView.let {
            /*if (isShowHintStr && t.code != Macro.CODE_SUCCESS) {
                mView.showMsg(t.msg)
            }*/

        }
    }

    /*   override fun onCompleted() {
        mView.let { it.hintLoading() }
    }*/
    override fun onComplete() {
        if (mView == null) {
            Log.e("onNext", "mView==null")
        } else {
            Log.e("onNext", "mView!=null")
        }
        mView.let { it.hintLoading() }
    }

    override fun onStart() {
        super.onStart()
        mView.let {
            if (isShowLoading)
                mView.stateLoading()
        }
    }

    override fun onError(e: Throwable?) {
        mView.let {
            it.hintLoading()

            when (e) {
                is ApiException -> {
                    var exception = e as ApiException
                    when (exception.code) {
//统一异常的处理
                        else -> {
                            mView.showMsg(exception.message)
                        }
                    }
                }
                is HttpException -> mView.let { it.showMsg("网络异常") }
                else -> mView.let {
                    it.showMsg("未知异常")
                    LogUtil.i("==================$e")
                }
            }

        }

    }

    /**
     * 真正处理数据
     *
     * @param t
     */
    open abstract fun handleData(t: T)


}