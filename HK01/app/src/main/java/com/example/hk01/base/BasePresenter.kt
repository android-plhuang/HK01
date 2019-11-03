package  com.example.hk01.base

import android.content.Context
import com.example.hk01.net.DataManager
import com.example.hk01.util.NetWorkUtils
import com.trello.rxlifecycle2.LifecycleProvider
import javax.inject.Inject

/**
 * 作者： hpl
 * 创建时间： 2018/8/31
 * email： shipotianhpl@163.com
 * 作用：presenter和view的绑定和解绑，使用
 */
/*
interface BasePresenter<T : BaseView> {
    fun attachView(t: T)
    fun detachView()
}*/

/*
    MVP中P层 基类
 */
open class BasePresenter<T:BaseView>{

    lateinit var mView:T

    @Inject
    lateinit var dataManager: DataManager


    //Dagger注入，Rx生命周期管理
    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>


    @Inject
    lateinit var context: Context

    /*
        检查网络是否可用
     */
    fun checkNetWork():Boolean{
        if(NetWorkUtils.isNetWorkAvailable(context)){
            return true
        }
        mView.showMsg("网络不可用")
        return false
    }
}
