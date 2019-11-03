package  com.example.hk01.presenter

import com.example.hk01.base.BasePresenter
import com.example.hk01.presenter.contract.EmptyContract
import javax.inject.Inject

/**
 * 作者： hpl
 * 创建时间： 2019/6/5
 * email： shipotianhpl@163.com
 * 作用：空数据的presenter，可以用于presenter没有确定的时候，确定后再替换成对应的presenter
 */
open class EmptyPresenter @Inject constructor() : BasePresenter<EmptyContract.View>(), EmptyContract.Presenter {

}