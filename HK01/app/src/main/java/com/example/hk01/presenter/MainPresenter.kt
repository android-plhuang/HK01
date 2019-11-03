package  com.example.hk01.presenter

import android.annotation.SuppressLint
import com.example.hk01.base.BasePresenter
import com.example.hk01.data.bean.TestBean
import com.example.hk01.net.http.CommonSubscriber
import com.example.hk01.net.http.RxUtil
import com.example.hk01.presenter.contract.MainContract
import javax.inject.Inject

/**
 * 作者： hpl
 * 创建时间： 2019/6/5
 * email： shipotianhpl@163.com
 * 作用：空数据的presenter，可以用于presenter没有确定的时候，确定后再替换成对应的presenter
 */
open class MainPresenter @Inject constructor() : BasePresenter<MainContract.View>(), MainContract.Presenter {
    @SuppressLint("CheckResult")
    override fun getList(number:Int) {
        dataManager.getList(number).compose(RxUtil.instance.rxSchedulerHelper<TestBean>())
            .compose(RxUtil.instance.handleResult<TestBean>())
            .compose(lifecycleProvider.bindToLifecycle<TestBean>())
            .subscribeWith(object : CommonSubscriber<TestBean>(this!!.mView!!,true,number==10) {
                override fun handleData(t: TestBean) {
                    (mView as MainContract.View)?.getListSuccess(t)
                }
            })
    }

    @SuppressLint("CheckResult")
    override fun geRecommendtList() {
        dataManager.getRecommendList().compose(RxUtil.instance.rxSchedulerHelper<TestBean>())
            .compose(RxUtil.instance.handleResult<TestBean>())
            .compose(lifecycleProvider.bindToLifecycle<TestBean>())
            .subscribeWith(object : CommonSubscriber<TestBean>(this!!.mView!!) {
                override fun handleData(t: TestBean) {

                    (mView as MainContract.View)?.getRecommendListSuccess(t)
                }
            })
    }
}